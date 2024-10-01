package ui.splash

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import ui.repository.UserRepository

class SplashScreenModel(
    private val userRepository: UserRepository
) : StateScreenModel<SplashScreenModel.State>(State.Init) {

    sealed interface State {
        data object Init : State
        data object Authorized : State
        data object Unauthorized : State
    }

    init {
        screenModelScope.launch {
            userRepository.getUser()
                .onSuccess { mutableState.value = State.Authorized }
                .onFailure { mutableState.value = State.Unauthorized }
        }
    }
}
