package data.model

import data.util.DateUtil.toIsoDateTimeFormat
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import ui.model.Stranger

@Serializable
data class StrangerResponse(
    val id: Long,
    val code: String,
    val nickname: String,
    val profile: String,
    val friendshipDate: String?,
    val blocked: Boolean
) {
    fun toUiModel() = Stranger(
        id = id,
        code = code,
        nickname = nickname,
        profileImageUrl = profile,
        friendshipDateTime = friendshipDate?.let { LocalDateTime.parse(it.toIsoDateTimeFormat()) },
        blocked = blocked
    )
}
