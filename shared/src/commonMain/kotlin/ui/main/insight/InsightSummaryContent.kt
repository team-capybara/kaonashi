package ui.main.insight

import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.haze
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ui.LocalHazeState
import ui.component.BOTTOM_NAV_BAR_HEIGHT
import ui.component.HOME_TOP_APP_BAR_HEIGHT
import ui.model.InsightSummary

@Composable
fun InsightSummaryContent(
    summary: InsightSummary,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    val hazeState = LocalHazeState.current
    val listState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.then(Modifier.fillMaxSize().haze(state = hazeState)),
        state = listState,
        contentPadding = PaddingValues(
            top = HOME_TOP_APP_BAR_HEIGHT + 8.dp,
            bottom = BOTTOM_NAV_BAR_HEIGHT + 8.dp,
            start = 16.dp,
            end = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
    ) {
        item {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        }
        items(InsightSummaryType.entries.size) { index ->
            InsightSummaryCard(
                type = InsightSummaryType.entries[index],
                summary = summary,
                onExpand = { expanded ->
                    if (expanded) {
                        val scrollOffset = with(density) {
                            ((INSIGHT_SUMMARY_CARD_HEIGHT + 8.dp) * index).toPx()
                        }
                        scope.launch {
                            delay(10L)
                            listState.animateScrollBy(scrollOffset)
                        }
                    }

                }
            )
        }
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    }
}
