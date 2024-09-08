package ui.main.insight

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.icerock.moko.resources.compose.painterResource
import team.capybara.moime.SharedRes
import ui.main.MainTab
import ui.main.TabView

object InsightTab : MainTab {

    override val tabViews: List<TabView> = InsightTabView.entries

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(SharedRes.images.ic_clipboard_text)

            return remember {
                TabOptions(
                    index = 1u,
                    title = "Statistics",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        InsightScreen().Content()
    }
}
