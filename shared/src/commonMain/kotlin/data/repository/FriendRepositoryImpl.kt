package data.repository

import data.Api
import data.model.FriendListResponse
import data.model.StrangerResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import ui.model.CursorData
import ui.model.CursorRequest
import ui.model.Friend
import ui.model.Stranger
import ui.repository.FriendRepository

class FriendRepositoryImpl(private val httpClient: HttpClient) : FriendRepository {

    override suspend fun getMyFriendsCount(): Result<Int> = runCatching {
        httpClient.get(Api.FRIENDS_COUNT).body<Int>()
    }

    override suspend fun getMyFriends(
        cursor: CursorRequest,
        nickname: String?
    ): Result<CursorData<Friend>> = runCatching {
        httpClient.get(Api.FRIENDS_FOLLOWINGS) {
            url {
                with(cursor) {
                    cursorId?.let { parameters.append("cursorId", it.toString()) }
                    limit?.let { parameters.append("size", it.toString()) }
                }
                nickname?.let { parameters.append("keyword", it) }
            }
        }.body<FriendListResponse>().run {
            CursorData(
                data = data.map { it.toUiModel() },
                nextCursorId = cursorId?.cursorId,
                isLast = last
            )
        }
    }

    override suspend fun getRecommendedFriends(
        cursor: CursorRequest,
        nickname: String?
    ): Result<CursorData<Friend>> = runCatching {
        httpClient.get(Api.FRIENDS_RECOMMENDED) {
            url {
                with(cursor) {
                    cursorId?.let { parameters.append("cursorId", it.toString()) }
                    limit?.let { parameters.append("size", it.toString()) }
                }
                nickname?.let { parameters.append("keyword", it) }
            }
        }.body<FriendListResponse>().run {
            CursorData(
                data = data.map { it.toUiModel() },
                nextCursorId = cursorId?.cursorId,
                isLast = last
            )
        }
    }

    override suspend fun getFriend(code: String): Result<Stranger> = runCatching {
        httpClient.get(Api.USERS_FIND) {
            url { parameters.append("userCode", code) }
        }.body<StrangerResponse>().toUiModel()
    }

    override suspend fun addFriend(targetId: Long): Result<Unit> = runCatching {
        httpClient.post(Api.FRIENDS_ADD) {
            url { parameters.append("targetId", targetId.toString()) }
        }.also { if (it.status.value != 200) throw RuntimeException(it.status.description) }
    }

    override suspend fun getBlockedFriendsCount(): Result<Int> = runCatching {
        httpClient.get(Api.FRIENDS_BLOCKED_COUNT).body<Int>()
    }

    override suspend fun getBlockedFriends(
        cursor: CursorRequest
    ): Result<CursorData<Friend>> = runCatching {
        httpClient.get(Api.FRIENDS_BLOCKED) {
            url {
                with(cursor) {
                    cursorId?.let { parameters.append("cursorId", it.toString()) }
                    limit?.let { parameters.append("size", it.toString()) }
                }
            }
        }.body<FriendListResponse>().run {
            CursorData(
                data = data.map { it.toUiModel() },
                nextCursorId = cursorId?.cursorId,
                isLast = last
            )
        }
    }

    override suspend fun blockFriend(targetId: Long): Result<Unit> = runCatching {
        httpClient.put(Api.FRIENDS_BLOCK) {
            url { parameters.append("targetId", targetId.toString()) }
        }.also { if (it.status.value != 200) throw Exception() }
    }

    override suspend fun unblockFriend(targetId: Long): Result<Unit> = runCatching {
        httpClient.put(Api.FRIENDS_UNBLOCK) {
            url { parameters.append("targetId", targetId.toString()) }
        }.also { if (it.status.value != 200) throw Exception() }
    }
}
