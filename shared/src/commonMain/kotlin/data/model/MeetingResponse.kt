package data.model

import data.util.DateUtil.toISO
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import ui.model.Location
import ui.model.Meeting
import ui.model.Participant

@Serializable
data class MeetingResponse(
    val data: List<MeetingResponseData>
)

@Serializable
data class MeetingResponseData(
    val id: Long,
    val title: String,
    val startedAt: String,
    val finishedAt: String?,
    val location: LocationResponse,
    val status: String,
    val participants: List<ParticipantResponse>,
    val bestPhotoUrl: String?
) {
    fun toUiModel() = Meeting(
        id = id,
        title = title,
        startDateTime = LocalDateTime.parse(startedAt.toISO()),
        finishDateTime = finishedAt?.let { LocalDateTime.parse(it.toISO()) },
        location = location.toUiModel(),
        status = Meeting.Status.from(status),
        participants = participants.map { it.toUiModel() },
        thumbnailUrl = bestPhotoUrl
    )
}

@Serializable
data class LocationResponse(
    val name: String,
    val latitude: Float,
    val longitude: Float
) {
    fun toUiModel() = Location(
        name = name,
        lat = latitude,
        lng = longitude
    )
}

@Serializable
data class ParticipantResponse(
    val userId: Long,
    val profileImageUrl: String
) {
    fun toUiModel() = Participant(
        id = userId,
        profileImageUrl = profileImageUrl
    )
}
