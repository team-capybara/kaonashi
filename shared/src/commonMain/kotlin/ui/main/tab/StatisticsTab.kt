package ui.main.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.chrisbanes.haze.HazeState
import team.capybara.moime.SharedRes
import ui.main.StatisticsScreen

data class StatisticsTab(val hazeState: HazeState) : MainTab {
    override val iconResource = SharedRes.images.ic_clipboard_text

    override val options: TabOptions
        @Composable
        get() = remember {
            TabOptions(
                index = 1u,
                title = "Statistics"
            )
        }

    @Composable
    override fun Content() {
        StatisticsScreen(hazeState)
    }
}
