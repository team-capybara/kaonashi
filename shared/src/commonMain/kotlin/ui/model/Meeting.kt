package ui.model

import kotlinx.datetime.LocalDateTime

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
        Completed("COMPLETED"),
        Unknown("UNKNOWN")
        ;

        companion object {
            fun from(status: String) = entries.find { it.value == status } ?: Unknown
        }
    }
}
