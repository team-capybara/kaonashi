package ui.repository

import ui.model.Friend

interface FriendRepository {

    suspend fun getFriendCount(): Int

    suspend fun getMyFriends(): List<Friend>

    suspend fun getRecommendedFriends(): List<Friend>
}
