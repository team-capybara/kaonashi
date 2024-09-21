package data.repository

import com.mmk.kmpnotifier.notification.NotifierManager
import com.russhwolf.settings.Settings
import data.Api
import data.model.UserResponse
import data.model.toUser
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.plugin
import io.ktor.client.request.get
import ui.login.LoginScreenModel.Companion.LOGIN_URL
import ui.model.User
import ui.repository.UserRepository

class UserRepositoryImpl(
    private val httpClient: HttpClient,
    private val settings: Settings
) : UserRepository {
    override suspend fun getUser(): Result<User> = runCatching {
        httpClient.get(Api.USERS_MY).body<UserResponse>().toUser()
    }

    override suspend fun login(accessToken: String): Result<String?> = runCatching {
        settings.putString("accessToken", accessToken)
        httpClient
            .plugin(Auth).providers
            .filterIsInstance<BearerAuthProvider>().firstOrNull()
            ?.refreshToken(httpClient.get(LOGIN_URL))
        NotifierManager.getPushNotifier().getToken()
    }
}
