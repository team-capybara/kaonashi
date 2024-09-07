package ui.main.insight

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import ui.main.MainScreenModel
import ui.theme.MoimeGreen

class InsightScreen : Screen {

    @Composable
    override fun Content() {
        val mainScreenModel = koinScreenModel<MainScreenModel>()
        val insightScreenModel = rememberScreenModel { InsightScreenModel() }
        val insightState by insightScreenModel.state.collectAsState()

        when (insightState) {
            is InsightScreenModel.State.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MoimeGreen)
                }
            }

            is InsightScreenModel.State.Result -> {
                when (mainScreenModel.tabViewState.currentInsightTabView) {
                    InsightTabView.Summary -> {
                        InsightSummaryContent()
                    }

                    InsightTabView.Friend -> {}
                }
            }
        }
    }
}
