package ui.main.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.icerock.moko.resources.compose.painterResource
import team.capybara.moime.SharedRes
import ui.meeting.camera.CameraScreen

object StatisticsTab : Tab {
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
        CameraScreen().Content()
    }
}
