package ui.main

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ui.component.MoimeBottomNavigationBar
import ui.main.tab.HomeTab
import ui.main.tab.StatisticsTab

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val mainScreenModel = rememberScreenModel { MainScreenModel() }

        TabNavigator(
            tab = HomeTab(mainScreenModel.hazeState),
            tabDisposable = {
                TabDisposable(
                    navigator = it,
                    tabs = listOf(
                        HomeTab(mainScreenModel.hazeState),
                        StatisticsTab(mainScreenModel.hazeState)
                    )
                )
            }
        ) {
            Scaffold(
                content = { CurrentTab() },
                bottomBar = { MoimeBottomNavigationBar(mainScreenModel.hazeState) }
            )
        }
    }
}
