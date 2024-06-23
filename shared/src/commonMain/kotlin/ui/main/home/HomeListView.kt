package ui.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
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
    onTodayMeetingVisibleChanged: (Boolean) -> Unit
) {
    val listState = rememberLazyListState()
    
    LaunchedEffect(Unit) {
        listState.scrollToItem(meetings.indexOfFirst { it.date.isToday() }.coerceAtLeast(0))
    }

    LaunchedEffect(listState.firstVisibleItemIndex) {
        println("FirstVisibleItemIndex changed!: ${listState.firstVisibleItemIndex}")
        onTodayMeetingVisibleChanged(meetings[listState.firstVisibleItemIndex].date.isToday())
    }

    LazyColumn(
        modifier = modifier,
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
                }
            )
        }
        item {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    }
}
