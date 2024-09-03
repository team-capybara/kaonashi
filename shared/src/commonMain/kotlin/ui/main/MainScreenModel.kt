package ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.Api
import data.model.UserResponse
import data.model.toUser
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.model.Meeting
import ui.model.User

class MainScreenModel : StateScreenModel<MainScreenModel.State>(State.Init), KoinComponent {
    private val httpClient: HttpClient by inject()

    var selectedDateMeetings by mutableStateOf<List<Meeting>?>(null)

    sealed interface State {
        data object Init : State
        data object Loading : State
        data class Authorized(val user: User) : State
        data object Unauthorized : State
    }

    init {
        mutableState.value = State.Loading
        screenModelScope.launch {
            val response = httpClient.get(Api.USERS_MY)
            when (response.status) {
                HttpStatusCode.OK -> {
                    mutableState.value = State.Authorized(response.body<UserResponse>().toUser())
                }

                HttpStatusCode.Unauthorized -> {
                    mutableState.value = State.Unauthorized
                }

                else -> {

                }
            }
        }
    }

    fun showMeetingsBottomSheet(meetings: List<Meeting>) {
        selectedDateMeetings = meetings
    }

    fun hideMeetingsBottomSheet() {
        selectedDateMeetings = null
    }

    fun getUser(): User = (mutableState.value as State.Authorized).user
}
