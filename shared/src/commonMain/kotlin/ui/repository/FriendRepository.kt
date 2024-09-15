package ui.repository

import ui.model.CursorData
import ui.model.CursorRequest
import ui.model.Friend

interface FriendRepository {

    suspend fun getFriendCount(): Int

    suspend fun getMyFriends(
        cursor: CursorRequest,
        nickname: String? = null
    ): CursorData<Friend>

    suspend fun getRecommendedFriends(
        cursor: CursorRequest,
        nickname: String? = null
    ): CursorData<Friend>
}
