package ui.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.haze
import dev.icerock.moko.resources.compose.fontFamilyResource
import io.wojciechosak.calendar.config.rememberCalendarState
import io.wojciechosak.calendar.utils.today
import io.wojciechosak.calendar.view.CalendarView
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.plus
import team.capybara.moime.SharedRes
import ui.LocalHazeState
import ui.component.BOTTOM_NAV_BAR_HEIGHT
import ui.component.HOME_TOP_APP_BAR_HEIGHT
import ui.model.Meeting
import ui.theme.Gray400
import ui.theme.Gray50
import ui.theme.Gray500
import ui.theme.Gray700
import ui.theme.MoimeGreen
import ui.util.DateUtil.toKr

@Composable
fun HomeCalendarView(
    modifier: Modifier = Modifier,
    meetings: List<Meeting>,
    onDayClicked: (List<Meeting>) -> Unit
) {
    val hazeState = LocalHazeState.current
    val density = LocalDensity.current
    val today = LocalDate.today()
    val minDate = today.minus(DatePeriod(months = 6))
    val maxDate = today.plus(DatePeriod(months = 6))
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = 7,
        initialFirstVisibleItemScrollOffset = -WindowInsets.statusBars.getTop(density)
    )

    LazyColumn(
        modifier = modifier.then(Modifier.fillMaxSize().haze(state = hazeState)),
        contentPadding = PaddingValues(
            top = HOME_TOP_APP_BAR_HEIGHT + 9.dp,
            bottom = BOTTOM_NAV_BAR_HEIGHT + 9.dp,
            start = 9.dp,
            end = 9.dp
        ),
        state = listState
    ) {
        item {
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
        }
        items(12) { monthOffset ->
            CalendarView(
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                config = rememberCalendarState(
                    startDate = today,
                    minDate = minDate,
                    maxDate = maxDate,
                    monthOffset = -6 + monthOffset
                ),
                verticalArrangement = Arrangement.Top,
                isActiveDay = { day -> meetings.map { it.startDateTime.date }.contains(day) },
                header = { month, _ ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, top = 12.dp, bottom = 12.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "${month.number}월",
                            color = Gray50,
                            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
                            fontSize = 16.sp
                        )
                    }
                },
                dayOfWeekLabel = { dayOfWeek ->
                    Text(
                        text = dayOfWeek.toKr(),
                        fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
                        fontSize = 16.sp,
                        color = Gray400,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                },
                day = { dayState ->
                    if (dayState.isForNextMonth || dayState.isForPreviousMonth) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .background(Gray500)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .background(
                                    if (dayState.isActiveDay) MoimeGreen else Gray500
                                )
                                .clickable {
                                    if (dayState.isActiveDay)
                                        onDayClicked(meetings.filter { it.startDateTime.date == dayState.date })
                                }
                        ) {
                            Text(
                                text = dayState.date.dayOfMonth.toString(),
                                fontFamily = fontFamilyResource(
                                    if (dayState.date == today) SharedRes.fonts.pretendard_semibold
                                    else SharedRes.fonts.pretendard_regular
                                ),
                                fontSize = 16.sp,
                                color = if (dayState.isActiveDay) {
                                    Gray700
                                } else if (dayState.date == LocalDate.today()) {
                                    MoimeGreen
                                } else {
                                    Gray50
                                },
                                modifier = Modifier.align(Alignment.Center)
                            )
                            if (dayState.date == today) {
                                Box(
                                    modifier = Modifier
                                        .padding(bottom = 10.dp)
                                        .clip(CircleShape)
                                        .background(if (dayState.isActiveDay) Gray700 else MoimeGreen)
                                        .size(4.dp)
                                        .align(Alignment.BottomCenter)
                                )
                            }
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
