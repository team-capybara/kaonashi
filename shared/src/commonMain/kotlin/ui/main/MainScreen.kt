package ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.IntSize
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.chrisbanes.haze.HazeState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.component.MeetingsBottomSheet
import ui.component.MoimeBottomNavigationBar
import ui.main.tab.HomeTab
import ui.main.tab.StatisticsTab
import ui.util.DeviceUtil

class MainScreen(screenSize: IntSize) : Screen, KoinComponent {

    private val hazeState: HazeState by inject()
    private val mainScreenModel: MainScreenModel by inject()

    init {
        DeviceUtil.screenSize = screenSize
    }

    @Composable
    override fun Content() {
        val state by mainScreenModel.state.collectAsState()

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
                    Box {
                        CurrentTab()
                        when (state) {
                            is MainScreenModel.State.Init -> {}
                            is MainScreenModel.State.ShowMeetingsBottomSheet -> {
                                MeetingsBottomSheet(
                                    meetings = (state as MainScreenModel.State.ShowMeetingsBottomSheet).meetings,
                                    onDismissRequest = { mainScreenModel.hideMeetingsBottomSheet() }
                                )
                            }
                        }
                    }
                },
                bottomBar = { MoimeBottomNavigationBar(hazeState) }
            )
        }
    }
}
