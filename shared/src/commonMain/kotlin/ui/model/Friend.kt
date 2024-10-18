package ui.model

import kotlinx.datetime.LocalDateTime

data class Friend(
    val id: Long,
    val nickname: String,
    val profileImageUrl: String,
    val friendshipDateTime: LocalDateTime?
)
