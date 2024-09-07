package ui.main.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.Api
import data.model.MeetingResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.model.Meeting

class HomeScreenModel : StateScreenModel<HomeScreenModel.State>(State.Loading()), KoinComponent {

    private val httpClient: HttpClient by inject()

    sealed interface State {
        data class Loading(val viaPulling: Boolean = false) : State
        data class Result(val meetings: List<Meeting>) : State
    }

    init {
        refreshMeetings(false)
    }

    fun refreshMeetings(viaPulling: Boolean) {
        screenModelScope.launch {
            mutableState.value = State.Loading(viaPulling)
            val allMeetings: List<Meeting> = listOf(
                Api.MOIMS_UPCOMING,
                Api.MOIMS_TODAY,
                Api.MOIMS_COMPLETE
            ).fold(emptyList()) { acc, api ->
                acc + httpClient.get(api) {
                    url {
                        parameters.append("size", GET_MEETINGS_QUERY_STRING_FOR_SIZE)
                    }
                }.body<MeetingResponse>().data.map { it.toUiModel() }
            }
            mutableState.value = State.Result(meetings = allMeetings.sortedBy { it.startDateTime })
        }
    }

    companion object {
        private const val GET_MEETINGS_QUERY_STRING_FOR_SIZE = "1000"
    }
}
