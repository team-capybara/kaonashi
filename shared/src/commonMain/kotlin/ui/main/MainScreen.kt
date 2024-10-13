package ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.ScreenTransition
import ui.component.FadeTransition
import ui.component.MeetingsBottomSheet
import ui.component.MoimeBottomNavigationBar
import ui.component.MoimeLoading
import ui.component.MoimeMainTopAppBar
import ui.friend.FriendScreen
import ui.main.home.HomeTab
import ui.main.insight.InsightTab
import ui.meeting.create.CreateScreen
import ui.meeting.detail.DetailScreen
import ui.model.User
import ui.mypage.MyPageScreen

@OptIn(ExperimentalVoyagerApi::class)
class MainScreen : Screen, ScreenTransition by FadeTransition() {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val mainScreenModel = koinScreenModel<MainScreenModel>()
        val mainState by mainScreenModel.state.collectAsState()
        var user by remember { mutableStateOf<User?>(null) }
        val selectedDateMeetings = mainScreenModel.selectedDateMeetings

        LaunchedEffect(mainState) {
            val state = mainState
            if (state is MainScreenModel.State.Success) {
                user = state.user
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
                            currentTabNavigator
                        ),
                        onClickProfile = { navigator.push(MyPageScreen()) },
                        onClickUserAdd = {
                            navigator.push(FriendScreen(user?.code ?: ""))
                        },
                        onClickNotification = {},
                        onTabViewChanged = {
                            mainScreenModel.setCurrentTabView(it)
                        },
                        hiddenBackground = mainScreenModel.topAppBarBackgroundVisible.not()
                    )
                },
                content = {
                    when (mainState) {
                        MainScreenModel.State.Loading -> MoimeLoading()
                        is MainScreenModel.State.Success -> {
                            Box {
                                CurrentTab()
                                selectedDateMeetings?.let { meetings ->
                                    MeetingsBottomSheet(
                                        meetings = meetings,
                                        onClickMeeting = { navigator.push(DetailScreen(it)) },
                                        onDismissRequest = { mainScreenModel.hideMeetingsBottomSheet() }
                                    )
                                }
                            }
                        }

                        else -> {}
                    }
                },
                bottomBar = {
                    MoimeBottomNavigationBar(
                        onAction = { navigator.push(CreateScreen()) }
                    )
                }
            )
        }
    }
}
