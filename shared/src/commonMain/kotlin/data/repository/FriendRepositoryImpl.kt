package data.repository

import data.Api
import data.model.FriendListResponse
import data.model.StrangerResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import ui.model.CursorData
import ui.model.CursorRequest
import ui.model.Friend
import ui.model.Stranger
import ui.repository.FriendRepository

class FriendRepositoryImpl(private val httpClient: HttpClient) : FriendRepository {

    override suspend fun getMyFriendsCount(): Int {
        return httpClient.get(Api.FRIENDS_COUNT).body<Int>()
    }

    override suspend fun getMyFriends(
        cursor: CursorRequest,
        nickname: String?
    ): CursorData<Friend> {
        return httpClient.get(Api.FRIENDS_FOLLOWINGS) {
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
    ): CursorData<Friend> {
        return httpClient.get(Api.FRIENDS_RECOMMENDED) {
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

    override suspend fun getFriend(code: String): Stranger? {
        val response = httpClient.get(Api.USERS_FIND) {
            url {
                parameters.append("userCode", code)
            }
        }
        return if (response.status == HttpStatusCode.OK) {
            response.body<StrangerResponse>().toUiModel()
        } else {
            null
        }
    }

    override suspend fun addFriend(targetId: Long): Boolean {
        val response = httpClient.post(Api.FRIENDS_ADD) {
            url {
                parameters.append("targetId", targetId.toString())
            }
        }
        return response.status == HttpStatusCode.OK
    }

    override suspend fun getBlockedFriendsCount(): Int {
        return httpClient.get(Api.FRIENDS_BLOCKED_COUNT).body<Int>()
    }

    override suspend fun getBlockedFriends(cursor: CursorRequest): CursorData<Friend> {
        return httpClient.get(Api.FRIENDS_BLOCKED) {
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

    override suspend fun blockFriend(targetId: Long): Boolean {
        val response = httpClient.post(Api.FRIENDS_BLOCK) {
            url {
                parameters.append("targetId", targetId.toString())
            }
        }
        return response.status == HttpStatusCode.OK
    }

    override suspend fun unblockFriend(targetId: Long): Boolean {
        val response = httpClient.post(Api.FRIENDS_UNBLOCK) {
            url {
                parameters.append("targetId", targetId.toString())
            }
        }
        return response.status == HttpStatusCode.OK
    }
}
