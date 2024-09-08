package ui.main.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.icerock.moko.resources.compose.painterResource
import team.capybara.moime.SharedRes
import ui.main.MainTab
import ui.main.TabView

object HomeTab : MainTab {

    override val tabViews: List<TabView> = HomeTabView.entries

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(SharedRes.images.ic_home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Home",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        HomeScreen().Content()
    }
}
