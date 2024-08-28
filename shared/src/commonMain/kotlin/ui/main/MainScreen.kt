package ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ui.component.MeetingsBottomSheet
import ui.component.MoimeBottomNavigationBar
import ui.component.MoimeLoading
import ui.login.LoginScreen
import ui.main.tab.HomeTab
import ui.main.tab.StatisticsTab

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val mainScreenModel = koinScreenModel<MainScreenModel>()
        val state by mainScreenModel.state.collectAsState()
        val selectedDateMeetings = mainScreenModel.selectedDateMeetings

        LaunchedEffect(state) {
            if(state == MainScreenModel.State.Unauthorized) {
                navigator.replace(LoginScreen())
            }
        }

        TabNavigator(
            tab = HomeTab,
            tabDisposable = {
                TabDisposable(
                    navigator = it,
                    tabs = listOf(
                        HomeTab,
                        StatisticsTab
                    )
                )
            }
        ) {
            Scaffold(
                content = {
                    when(state) {
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
