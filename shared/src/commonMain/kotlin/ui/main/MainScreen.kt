package ui.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.chrisbanes.haze.HazeState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.component.MoimeBottomNavigationBar
import ui.main.tab.HomeTab
import ui.main.tab.StatisticsTab

class MainScreen : Screen, KoinComponent {

    private val hazeState: HazeState by inject()

    @Composable
    override fun Content() {
        val mainScreenModel = rememberScreenModel { MainScreenModel() }

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
                content = { CurrentTab() },
                bottomBar = { MoimeBottomNavigationBar(hazeState) }
            )
        }
    }
}
