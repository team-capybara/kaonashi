package ui.main.home

import io.wojciechosak.calendar.utils.today
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import ui.model.CursorData
import ui.model.Meeting
import ui.util.DateUtil.isToday

data class HomeState(
    val homeListState: HomeListState = HomeListState(),
    val homeCalendarState: HomeCalendarState = HomeCalendarState()
) {
    val isLoading: Boolean = homeListState.isLoading && homeCalendarState.isLoading

    fun listStateLoading(value: Boolean) =
        copy(homeListState = homeListState.copy(isLoading = value))

    fun calendarStateLoading(value: Boolean) =
        copy(homeCalendarState = homeCalendarState.copy(isLoading = value))

    fun loadListCompletedMeetings() =
        copy(homeListState = homeListState.copy(completedMeetings = homeListState.completedMeetings.loading()))

    fun setUpcomingMeetings(value: List<Meeting>) =
        copy(homeListState = homeListState.copy(upcomingMeetings = value))

    fun setOngoingMeetings(value: List<Meeting>) =
        copy(homeListState = homeListState.copy(ongoingMeetings = value))

    fun updateCompletedMeetings(next: CursorData<Meeting>) = copy(
        homeListState = homeListState.copy(
            completedMeetings = homeListState.completedMeetings.concatenate(next)
        )
    )

    fun setMeetingCount(value: Map<LocalDate, Int>) =
        copy(
            homeCalendarState = homeCalendarState.copy(
                meetingCount = value,
                isLoading = false
            )
        )
}

data class HomeListState(
    val isLoading: Boolean = true,
    val completedMeetings: CursorData<Meeting> = CursorData(),
    val ongoingMeetings: List<Meeting>? = null,
    val upcomingMeetings: List<Meeting>? = null
) {
    val isMeetingInitialized: Boolean = ongoingMeetings != null && upcomingMeetings != null

    val meetings: List<Meeting> =
        ((upcomingMeetings ?: emptyList()) +
                (ongoingMeetings ?: emptyList()) +
                completedMeetings.data)
            .sortedByDescending { it.startDateTime }

    val initialVisibleMeetingIndex: Int = if (ongoingMeetings != null && upcomingMeetings != null) {
        val index = ongoingMeetings.indexOfFirst { it.startDateTime.isToday() }
        if (index != -1) upcomingMeetings.size + index
        else 0
    } else {
        0
    }
}

data class HomeCalendarState(
    val isLoading: Boolean = true,
    val minDate: LocalDate = LocalDate.today().minus(DatePeriod(months = 6)),
    val maxDate: LocalDate = LocalDate.today().plus(DatePeriod(months = 6)),
    val meetingCount: Map<LocalDate, Int> = emptyMap()
)
