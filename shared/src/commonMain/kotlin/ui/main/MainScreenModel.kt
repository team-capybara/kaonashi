package ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ui.model.Meeting
import ui.model.User
import ui.repository.UserRepository

class MainScreenModel(
    private val userRepository: UserRepository
) : StateScreenModel<MainScreenModel.State>(State.Init) {

    var tabViewState by mutableStateOf(MainTabViewState())
        private set
    var topAppBarBackgroundVisible by mutableStateOf(true)
        private set
    var selectedDateMeetings by mutableStateOf<List<Meeting>?>(null)
        private set

    sealed interface State {
        data object Init : State
        data object Loading : State
        data class Authorized(val user: User) : State
        data object Unauthorized : State
    }

    init {
        mutableState.value = State.Loading
        screenModelScope.launch {
            userRepository.getUser()
                .onSuccess { mutableState.value = State.Authorized(it) }
                .onFailure { mutableState.value = State.Unauthorized }
        }
    }

    fun setTopAppBarBackgroundVisibility(visible: Boolean) {
        topAppBarBackgroundVisible = visible
    }

    fun setCurrentTabView(tabView: MainTabView) {
        tabViewState = tabViewState.copy(tabView)
    }

    fun showMeetingsBottomSheet(meetings: List<Meeting>) {
        selectedDateMeetings = meetings
    }

    fun hideMeetingsBottomSheet() {
        selectedDateMeetings = null
    }

    fun getUser(): User? = when (val currentState = state.value) {
        is State.Authorized -> currentState.user
        else -> null
    }
}
