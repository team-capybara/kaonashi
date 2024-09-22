package ui.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import ui.util.DateUtil.now
import ui.util.DateUtil.timeZone

data class Stranger(
    val id: Long,
    val code: String,
    val nickname: String,
    val profileImageUrl: String,
    val friendshipDateTime: LocalDateTime?
) {
    // createdDateTime is not a explicit value, but a temporary value
    fun toFriend() = Friend(
        id = id,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
        createdDateTime = LocalDateTime.now().toLocalDateTime(timeZone)
    )
}
