package ui.model

import kotlinx.datetime.LocalDateTime

data class Stranger(
    val id: Long,
    val code: String,
    val nickname: String,
    val profileImageUrl: String,
    val friendshipDateTime: LocalDateTime?
)
