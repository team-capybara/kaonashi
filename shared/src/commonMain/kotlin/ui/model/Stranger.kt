package ui.model

import kotlinx.datetime.LocalDateTime
import ui.util.DateUtil.now

data class Stranger(
    val id: Long,
    val code: String,
    val nickname: String,
    val profileImageUrl: String,
    val friendshipDateTime: LocalDateTime?,
    val blocked: Boolean
) {
    fun toFriend() = Friend(
        id = id,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
        friendshipDateTime = friendshipDateTime
    )

    companion object {
        fun init(id: Long) = Stranger(
            id = id,
            code = "",
            nickname = "",
            profileImageUrl = "",
            friendshipDateTime = LocalDateTime.now(),
            blocked = false
        )
    }
}
