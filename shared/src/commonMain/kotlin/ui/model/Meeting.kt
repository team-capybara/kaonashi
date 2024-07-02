package ui.model

import kotlinx.datetime.LocalDateTime

data class Meeting(
    val key: Long,
    val title: String,
    val dateTime: LocalDateTime,
    val location: String,
    val participants: List<User>,
    val thumbnailUrl: String? = null
)
