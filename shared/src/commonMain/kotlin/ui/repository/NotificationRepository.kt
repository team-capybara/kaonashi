package ui.repository

interface NotificationRepository {

    suspend fun hasUnreadNotification(): Result<Boolean>
}
