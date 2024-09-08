package ui.friend

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.model.Friend
import ui.repository.FriendRepository

class FriendScreenModel : StateScreenModel<FriendScreenModel.State>(State()), KoinComponent {

    private val friendRepository: FriendRepository by inject()

    data class State(
        val isFriendListLoading: Boolean = true,
        val friendCount: Int = 0,
        val myFriends: List<Friend> = emptyList(),
        val recommendedFriends: List<Friend> = emptyList()
    )

    init {
        getFriendCount()
        getMyFriends()
        getRecommendedFriends()
    }

    fun getFriendCount() {
        screenModelScope.launch {
            val friendCount = friendRepository.getFriendCount()
            mutableState.value = state.value.copy(
                isFriendListLoading = false,
                friendCount = friendCount
            )
        }
    }

    fun getMyFriends() {
        screenModelScope.launch {
            mutableState.value = state.value.copy(isFriendListLoading = true)
            val myFriends = friendRepository.getMyFriends()
            mutableState.value = state.value.copy(
                isFriendListLoading = false,
                myFriends = myFriends
            )
        }
    }

    fun getRecommendedFriends() {
        screenModelScope.launch {
            mutableState.value = state.value.copy(isFriendListLoading = true)
            val recommendedFriends = friendRepository.getRecommendedFriends()
            mutableState.value = state.value.copy(
                isFriendListLoading = false,
                recommendedFriends = recommendedFriends
            )
        }
    }
}
