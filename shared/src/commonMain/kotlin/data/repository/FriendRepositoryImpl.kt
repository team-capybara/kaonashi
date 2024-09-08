package data.repository

import io.ktor.client.HttpClient
import ui.model.Friend
import ui.model.dummyFriends
import ui.repository.FriendRepository

class FriendRepositoryImpl(private val httpClient: HttpClient) : FriendRepository {

    override suspend fun getFriendCount(): Int {
        return 15
    }

    override suspend fun getMyFriends(): List<Friend> {
        return dummyFriends
    }

    override suspend fun getRecommendedFriends(): List<Friend> {
        return dummyFriends
    }
}
