package ui.main.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import ui.model.Meeting
import ui.repository.MeetingRepository

class HomeScreenModel(
    private val meetingRepository: MeetingRepository
) : StateScreenModel<HomeState>(HomeState()) {

    init {
        refresh()
    }

    fun refresh() {
        println("refreshed homeScreen")
        refreshListState()
        refreshCalendarState()
    }

    fun loadCompleteMeetings() {
        if (state.value.homeListState.completedMeetings.canRequest().not()) return
        screenModelScope.launch {
            mutableState.value = state.value.loadListCompletedMeetings()
            meetingRepository.getCompletedMeetings(state.value.homeListState.completedMeetings.nextRequest())
                .onSuccess {
                    mutableState.value = state.value.updateCompletedMeetings(it)
                    mutableState.value = state.value.listStateLoading(false)
                }
                .onFailure { println("failed to load completed meetings: ${it.message}") }
        }
    }

    private fun getOngoingMeetings() {
        screenModelScope.launch {
            meetingRepository.getAllOngoingMeetings()
                .onSuccess { mutableState.value = state.value.setOngoingMeetings(it) }
                .onFailure { println("failed to load ongoing meetings: ${it.message}") }
        }
    }

    private fun getUpcomingMeetings() {
        screenModelScope.launch {
            meetingRepository.getAllUpcomingMeetings()
                .onSuccess { mutableState.value = state.value.setUpcomingMeetings(it) }
                .onFailure { println("failed to load upcoming meetings: ${it.message}") }
        }
    }

    fun refreshListState() {
        mutableState.value = state.value.copy(homeListState = HomeListState())
        getUpcomingMeetings()
        getOngoingMeetings()
        loadCompleteMeetings()
    }

    fun refreshCalendarState() {
        mutableState.value = state.value.copy(homeCalendarState = HomeCalendarState())
        getMeetingCount()
    }

    private fun getMeetingCount(
        from: LocalDate = state.value.homeCalendarState.minDate,
        to: LocalDate = state.value.homeCalendarState.maxDate
    ) {
        screenModelScope.launch {
            meetingRepository.getMeetingsCount(from, to)
                .onSuccess { mutableState.value = state.value.setMeetingCount(it) }
                .onFailure { /* failed to load meeting count */ }
        }
    }

    fun loadMeetingOfDay(date: LocalDate, callback: (List<Meeting>) -> Unit) {
        screenModelScope.launch {
            meetingRepository.getMeetingsOfDay(date)
                .onSuccess { callback(it) }
                .onFailure { /* failed to load meetings of day */ }
        }
    }
}
