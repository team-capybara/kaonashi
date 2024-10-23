package data.repository

import data.Api
import data.model.ApiException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ui.repository.NotificationRepository

class NotificationRepositoryImpl(
    private val httpClient: HttpClient
) : NotificationRepository {

    override suspend fun hasUnreadNotification(): Result<Boolean> = runCatching {
        httpClient.get(Api.NOTIFICATION_EXIST).run {
            if (status.value != 200) {
                throw ApiException(status)
            } else {
                body<Boolean>()
            }
        }
    }
}
