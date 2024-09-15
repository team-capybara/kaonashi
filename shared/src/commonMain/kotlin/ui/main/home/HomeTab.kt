package ui.main.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.icerock.moko.resources.compose.painterResource
import team.capybara.moime.SharedRes
import ui.main.MainTab
import ui.main.MainTabView

object HomeTab : MainTab {

    override val tabViews: List<MainTabView> = HomeTabView.entries

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
