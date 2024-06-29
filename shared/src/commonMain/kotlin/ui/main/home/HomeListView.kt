package ui.main.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import ui.component.BOTTOM_NAV_BAR_HEIGHT
import ui.component.HOME_TOP_APP_BAR_HEIGHT
import ui.component.MoimeMeetingCard
import ui.model.Meeting
import ui.util.DateUtil.isToday

@Composable
fun HomeListView(
    modifier: Modifier = Modifier,
    meetings: List<Meeting>,
    isTodayMeetingVisible: Boolean,
    onTodayMeetingVisibleChanged: (Boolean) -> Unit,
    innerPadding: PaddingValues
) {
    val density = LocalDensity.current
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = meetings.indexOfFirst { it.date.isToday() }.coerceAtLeast(0),
    )

    LaunchedEffect(listState.firstVisibleItemIndex) {
        onTodayMeetingVisibleChanged(meetings[listState.firstVisibleItemIndex].date.isToday())
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = isTodayMeetingVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradientBrush)
            )
        }
        LazyColumn(
            modifier = modifier.then(Modifier.fillMaxSize()),
            state = listState,
            contentPadding =
            PaddingValues(
                top = HOME_TOP_APP_BAR_HEIGHT + 8.dp,
                bottom = BOTTOM_NAV_BAR_HEIGHT + 8.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            item {
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            }
            items(meetings.size) {
                MoimeMeetingCard(
                    meeting = meetings[it],
                    isAnotherTodayMeetingCardFocusing = run {
                        val currentScrollIndex = listState.firstVisibleItemIndex
                        meetings[currentScrollIndex].date.isToday() && it != currentScrollIndex
                    },
                    innerPadding = innerPadding
                )
            }
            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
            }
        }
    }
}

private val colorStops = arrayOf(
    0.0f to Color(0xCC25FF89),
    0.67f to Color(0x0000E8BE),
)
private val gradientBrush = Brush.verticalGradient(colorStops = colorStops)
