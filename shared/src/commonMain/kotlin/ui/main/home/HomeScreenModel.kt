package ui.main.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.model.Meeting
import ui.repository.MeetingRepository

class HomeScreenModel : StateScreenModel<HomeScreenModel.State>(State.Loading()), KoinComponent {

    private val meetingRepository: MeetingRepository by inject()

    sealed interface State {
        data class Loading(val viaPulling: Boolean = false) : State
        data class Success(val meetings: List<Meeting>) : State
        data class Failure(val throwable: Throwable?) : State
    }

    init {
        loadMeetings(false)
    }

    fun loadMeetings(viaPulling: Boolean) {
        screenModelScope.launch {
            mutableState.value = State.Loading(viaPulling)
            meetingRepository.getAllMeetings()
                .onSuccess { mutableState.value = State.Success(it) }
                .onFailure { mutableState.value = State.Failure(it) }
        }
    }
}
