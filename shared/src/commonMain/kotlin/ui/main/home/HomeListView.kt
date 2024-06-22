package ui.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.component.BOTTOM_NAV_BAR_HEIGHT
import ui.component.HOME_TOP_APP_BAR_HEIGHT
import ui.component.MoimeMeetingCard
import ui.model.Meeting

@Composable
fun HomeListView(
    modifier: Modifier = Modifier,
    meetings: List<Meeting>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding =
        PaddingValues(
            top = HOME_TOP_APP_BAR_HEIGHT - 8.dp,
            bottom = BOTTOM_NAV_BAR_HEIGHT + 8.dp,
            start = 16.dp,
            end = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        items(meetings) {
            MoimeMeetingCard(it)
        }
    }
}
