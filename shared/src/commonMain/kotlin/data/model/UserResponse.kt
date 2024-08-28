package data.model

import kotlinx.serialization.Serializable
import ui.model.ProviderType
import ui.model.User

@Serializable
data class UserResponse(
    val id: Long,
    val code: String,
    val nickname: String,
    val email: String,
    val providerType: String,
    val profile: String
)

fun UserResponse.toUser(): User = User(
    id = id,
    code = code,
    nickname = nickname,
    email = email,
    providerType = ProviderType.valueOf(providerType),
    profileImageUrl = profile
)
