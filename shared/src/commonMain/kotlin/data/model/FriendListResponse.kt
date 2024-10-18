package data.model

import data.util.DateUtil.toIsoDateTimeFormat
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import ui.model.Friend

@Serializable
data class FriendListResponse(
    val data: List<FriendListResponseData>,
    val last: Boolean,
    val cursorId: Cursor?
)

@Serializable
data class FriendListResponseData(
    val targetId: Long,
    val targetNickname: String,
    val targetProfile: String,
    val friendCreatedAt: String
) {
    fun toUiModel() = Friend(
        id = targetId,
        nickname = targetNickname,
        profileImageUrl = targetProfile,
        friendshipDateTime = LocalDateTime.parse(friendCreatedAt.toIsoDateTimeFormat())
    )
}

@Serializable
data class Cursor(
    val cursorId: Int
)
