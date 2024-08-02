package ui.main.insight

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.component.MoimeMainTopAppBar
import ui.main.home.HomeCalendarView
import ui.main.home.HomeListView
import ui.main.home.HomeScreenModel
import ui.main.home.HomeTabView
import ui.theme.MoimeGreen

class InsightScreen : Screen {

    @Composable
    override fun Content() {
        val insightScreenModel = rememberScreenModel { InsightScreenModel() }
        val state by insightScreenModel.state.collectAsState()

        var currentTabView by remember { mutableStateOf(InsightTabView.Summary) }

        Scaffold(
            topBar = {
                MoimeMainTopAppBar(
                    profileImageUrl = "https://play-lh.googleusercontent.com/Kbu0747Cx3rpzHcSbtM1zDriGFG74zVbtkPmVnOKpmLCS59l7IuKD5M3MKbaq_nEaZM",
                    tabViews = InsightTabView.entries,
                    selectedTabView = currentTabView,
                    onClickUserAdd = {},
                    onClickNotification = {},
                    onClickFirstTabView = { currentTabView = InsightTabView.Summary },
                    onClickSecondTabView = { currentTabView = InsightTabView.Friend },
                    hiddenBackground = false
                )
            },
        ) { innerPadding ->
            when (state) {
                is InsightScreenModel.State.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MoimeGreen)
                    }
                }

                is InsightScreenModel.State.Result -> {
                    when (currentTabView) {
                        InsightTabView.Summary -> {}
                        InsightTabView.Friend -> {}
                    }
                }
            }
        }
    }
}
