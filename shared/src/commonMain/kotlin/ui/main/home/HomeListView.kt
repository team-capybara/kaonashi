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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.haze
import dev.materii.pullrefresh.PullRefreshIndicator
import dev.materii.pullrefresh.PullRefreshLayout
import dev.materii.pullrefresh.rememberPullRefreshState
import moime.shared.generated.resources.Res
import moime.shared.generated.resources.ic_chevron_down
import moime.shared.generated.resources.ic_chevron_up
import org.jetbrains.compose.resources.painterResource
import ui.LocalHazeState
import ui.component.BOTTOM_NAV_BAR_HEIGHT
import ui.component.HOME_TOP_APP_BAR_HEIGHT
import ui.component.MOIME_CARD_HEIGHT
import ui.component.MoimeMeetingCard
import ui.model.Meeting
import ui.theme.Gray50
import ui.theme.MoimeGreen
import ui.util.DateUtil.isToday

@Composable
fun HomeListView(
    modifier: Modifier = Modifier,
    meetings: List<Meeting>,
    homeState: HomeScreenModel.State,
    onRefresh: () -> Unit,
    isTodayMeetingVisible: Boolean,
    onTodayMeetingVisibleChanged: (Boolean) -> Unit
) {
    val hazeState = LocalHazeState.current
    val density = LocalDensity.current
    val pullRefreshState = rememberPullRefreshState(
        refreshing = homeState is HomeScreenModel.State.Loading,
        onRefresh = onRefresh
    )
    val firstVisibleItemIndex =
        meetings.indexOfFirst { it.startDateTime.isToday() }.coerceAtLeast(0)
    val listState = rememberLazyListState(
        initialFirstVisibleItemScrollOffset = if (firstVisibleItemIndex == 0) {
            0
        } else {
            with(density) {
                (MOIME_CARD_HEIGHT.times(
                    firstVisibleItemIndex
                ) + 8.dp.times(firstVisibleItemIndex - 3)).roundToPx()
            }
        }
    )

    LaunchedEffect(listState.firstVisibleItemIndex) {
        onTodayMeetingVisibleChanged(meetings[listState.firstVisibleItemIndex].startDateTime.isToday())
    }

    PullRefreshLayout(
        modifier = modifier.then(Modifier.fillMaxSize()),
        state = pullRefreshState,
        indicator = {
            PullRefreshIndicator(
                state = pullRefreshState,
                backgroundColor = MaterialTheme.colorScheme.background,
                contentColor = MoimeGreen,
                modifier = Modifier.padding(top = with(density) {
                    WindowInsets.statusBars.getTop(this).toDp()
                } + HOME_TOP_APP_BAR_HEIGHT)
            )
        }
    ) {
        AnimatedVisibility(
            visible = isTodayMeetingVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradientBrush)
            ) {
                if (listState.firstVisibleItemIndex != 0) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_chevron_up),
                        contentDescription = null,
                        tint = Gray50,
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.statusBars)
                            .padding(top = HOME_TOP_APP_BAR_HEIGHT + 8.dp)
                            .size(24.dp)
                            .align(Alignment.TopCenter)
                    )
                }
                if (listState.firstVisibleItemIndex != meetings.lastIndex) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_chevron_down),
                        contentDescription = null,
                        tint = Gray50,
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.navigationBars)
                            .padding(bottom = BOTTOM_NAV_BAR_HEIGHT + 8.dp)
                            .size(24.dp)
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize().haze(state = hazeState),
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
            items(meetings.size) {
                MoimeMeetingCard(
                    meeting = meetings[it],
                    isAnotherTodayMeetingCardFocusing = run {
                        val currentScrollIndex = listState.firstVisibleItemIndex
                        meetings[currentScrollIndex].startDateTime.isToday() && it != currentScrollIndex
                    }
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
