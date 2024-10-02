package ui.model

import kotlinx.datetime.LocalDateTime
import ui.model.Meeting.Status.Unknown
import ui.util.DateUtil.now

data class Meeting(
    val id: Long,
    val title: String,
    val startDateTime: LocalDateTime,
    val finishDateTime: LocalDateTime?,
    val status: Status,
    val location: Location,
    val participants: List<Participant>,
    val thumbnailUrl: String? = null
) {
    enum class Status(val value: String) {
        Created("CREATED"),
        Ongoing("ONGOING"),
        Completed("COMPLETED"),
        Finished("FINISHED"),
        Failed("FAILED"),
        Unknown("UNKNOWN")
        ;

        companion object {
            fun from(status: String) = entries.find { it.value == status } ?: Unknown
        }
    }

    companion object {
        // for HomeListView pagination trigger
        fun fake() = Meeting(
            -1,
            "",
            LocalDateTime.now(),
            null,
            Unknown,
            Location("", 0f, 0f),
            emptyList(),
            ""
        )
    }
}
