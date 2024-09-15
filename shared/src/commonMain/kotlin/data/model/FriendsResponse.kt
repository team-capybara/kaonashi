package data.model

import data.util.DateUtil.toIsoDateTimeFormat
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import ui.model.Friend

@Serializable
data class FriendsResponse(
    val data: List<FriendsResponseData>,
    val last: Boolean,
    val cursorId: Cursor?
)

@Serializable
data class FriendsResponseData(
    val targetId: Long,
    val targetNickname: String,
    val targetProfile: String,
    val friendCreatedAt: String
) {
    fun toUiModel() = Friend(
        id = targetId,
        nickname = targetNickname,
        profileImageUrl = targetProfile,
        createdDateTime = LocalDateTime.parse(friendCreatedAt.toIsoDateTimeFormat())
    )
}

@Serializable
data class Cursor(
    val cursorId: Int
)
