package ui.repository

import ui.model.CursorData
import ui.model.CursorRequest
import ui.model.Friend
import ui.model.Stranger

interface FriendRepository {

    suspend fun getMyFriendsCount(): Int

    suspend fun getMyFriends(
        cursor: CursorRequest,
        nickname: String? = null
    ): CursorData<Friend>

    suspend fun getRecommendedFriends(
        cursor: CursorRequest,
        nickname: String? = null
    ): CursorData<Friend>

    suspend fun getFriend(code: String): Stranger
}
