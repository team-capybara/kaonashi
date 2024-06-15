package ui.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.chrisbanes.haze.HazeState
import ui.component.MoimeBottomNavigationBar
import ui.main.tab.HomeTab
import ui.main.tab.StatisticsTab

@Composable
fun MainScreen() {
    val hazeState = remember { HazeState() }

    TabNavigator(
        tab = HomeTab(hazeState),
        tabDisposable = {
            TabDisposable(
                navigator = it,
                tabs = listOf(HomeTab(hazeState), StatisticsTab(hazeState))
            )
        }
    ) {
        Scaffold(
            content = { CurrentTab() },
            bottomBar = { MoimeBottomNavigationBar(hazeState) }
        )
    }
}
