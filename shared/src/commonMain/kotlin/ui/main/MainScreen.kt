package ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ui.component.MeetingsBottomSheet
import ui.component.MoimeBottomNavigationBar
import ui.component.MoimeLoading
import ui.component.MoimeMainTopAppBar
import ui.friend.FriendScreen
import ui.login.LoginScreen
import ui.main.home.HomeTab
import ui.main.insight.InsightTab
import ui.model.User

class MainScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val mainScreenModel = koinScreenModel<MainScreenModel>()
        val mainState by mainScreenModel.state.collectAsState()

        var user by mutableStateOf<User?>(null)
        val selectedDateMeetings = mainScreenModel.selectedDateMeetings

        LaunchedEffect(mainState) {
            when (val state = mainState) {
                is MainScreenModel.State.Authorized -> {
                    user = state.user
                }

                MainScreenModel.State.Unauthorized -> {
                    navigator.replace(LoginScreen())
                }

                else -> {

                }
            }
        }

        TabNavigator(
            tab = HomeTab,
            tabDisposable = {
                TabDisposable(
                    navigator = it,
                    tabs = listOf(HomeTab, InsightTab)
                )
            }
        ) { tabNavigator ->
            Scaffold(
                topBar = {
                    val currentTabNavigator = tabNavigator.current as MainTab
                    MoimeMainTopAppBar(
                        profileImageUrl = user?.profileImageUrl ?: "",
                        currentTab = currentTabNavigator,
                        currentTabView = mainScreenModel.tabViewState.getCurrentTabViewWithTab(
                            tabNavigator.current
                        ),
                        onClickUserAdd = {
                            navigator.push(FriendScreen(user?.code ?: ""))
                        },
                        onClickNotification = {},
                        onClickFirstTabView = {
                            mainScreenModel.setCurrentTabView((currentTabNavigator).tabViews[0])
                        },
                        onClickSecondTabView = {
                            mainScreenModel.setCurrentTabView((currentTabNavigator).tabViews[1])
                        },
                        hiddenBackground = mainScreenModel.topAppBarBackgroundVisible.not()
                    )
                },
                content = {
                    when (mainState) {
                        MainScreenModel.State.Loading -> MoimeLoading()
                        is MainScreenModel.State.Authorized -> {
                            Box {
                                CurrentTab()
                                selectedDateMeetings?.let {
                                    MeetingsBottomSheet(
                                        meetings = it,
                                        onDismissRequest = { mainScreenModel.hideMeetingsBottomSheet() }
                                    )
                                }
                            }
                        }

                        else -> {}
                    }
                },
                bottomBar = { MoimeBottomNavigationBar() }
            )
        }
    }
}
