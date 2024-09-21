package data.repository

import data.Api
import data.model.MeetingResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ui.model.Meeting
import ui.repository.MeetingRepository

class MeetingRepositoryImpl(
    private val httpClient: HttpClient
) : MeetingRepository {

    override suspend fun getAllMeetings(): Result<List<Meeting>> = runCatching {
        listOf(
            Api.MOIMS_UPCOMING,
            Api.MOIMS_TODAY,
            Api.MOIMS_COMPLETE
        ).fold<String, List<Meeting>>(emptyList()) { acc, api ->
            acc + httpClient.get(api) {
                url {
                    parameters.append("size", "100")
                }
            }.body<MeetingResponse>().data.map { it.toUiModel() }
        }.sortedBy { it.startDateTime }
    }
}
