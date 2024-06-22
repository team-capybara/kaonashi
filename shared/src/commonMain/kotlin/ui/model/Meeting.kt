package ui.model

import kotlinx.datetime.LocalDateTime

data class Meeting(
    val key: Long,
    val title: String,
    val date: LocalDateTime,
    val location: String,
    val participants: List<User>,
    val thumbnailUrl: String? = null
)
