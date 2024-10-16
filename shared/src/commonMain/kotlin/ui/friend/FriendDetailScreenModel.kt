package ui.friend

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import moime.shared.generated.resources.Res
import moime.shared.generated.resources.block
import moime.shared.generated.resources.block_friend_dialog
import moime.shared.generated.resources.block_friend_dialog_desc
import moime.shared.generated.resources.cancel
import moime.shared.generated.resources.confirm
import moime.shared.generated.resources.failed_to_block_friend
import moime.shared.generated.resources.failed_to_block_friend_desc
import moime.shared.generated.resources.failed_to_unblock_friend
import moime.shared.generated.resources.failed_to_unblock_friend_desc
import org.jetbrains.compose.resources.getString
import ui.component.DialogRequest
import ui.model.CursorData
import ui.model.Meeting
import ui.model.Stranger
import ui.repository.FriendRepository
import ui.repository.MeetingRepository

class FriendDetailScreenModel(
    private val targetId: Long,
    private val friendRepository: FriendRepository,
    private val meetingRepository: MeetingRepository
) : StateScreenModel<FriendDetailScreenModel.State>(State()) {

    data class State(
        val stranger: Stranger? = null,
        val meetings: CursorData<Meeting> = CursorData(),
        val meetingsTotalCount: Int = 0,
        val dialogRequest: DialogRequest? = null,
    )

    init {
        refresh()
    }

    private fun refresh() {
        getStranger()
        getMeetingsTotalCount()
        loadMeetings()
    }

    private fun getStranger() {
        screenModelScope.launch {
            friendRepository.getStranger(targetId)
                .onSuccess { stranger ->
                    mutableState.value = state.value.copy(stranger = stranger)
                }
        }
    }

    private fun getMeetingsTotalCount() {
        screenModelScope.launch {
            meetingRepository.getMeetingsCountWith(targetId)
                .onSuccess { totalCount ->
                    mutableState.value = state.value.copy(meetingsTotalCount = totalCount)
                }
        }
    }

    fun loadMeetings() {
        if (state.value.meetings.canRequest().not()) return
        screenModelScope.launch {
            mutableState.value = state.value.copy(meetings = state.value.meetings.loading())
            meetingRepository.getMeetingsWith(targetId, state.value.meetings.nextRequest())
                .onSuccess { nextMeetings ->
                    mutableState.value = state.value.copy(
                        meetings = state.value.meetings.concatenate(nextMeetings)
                    )
                }.onFailure {
                    /* failed to load meetings */
                    mutableState.value =
                        state.value.copy(meetings = state.value.meetings.loading(false))
                }
        }
    }

    fun block() {
        val targetNickname = state.value.stranger?.nickname ?: return
        screenModelScope.launch {
            showDialog(
                DialogRequest(
                    title = getString(Res.string.block_friend_dialog, targetNickname),
                    description = getString(Res.string.block_friend_dialog_desc, targetNickname),
                    actionTextRes = Res.string.block,
                    subActionTextRes = Res.string.cancel,
                    onAction = ::onBlock,
                    onSubAction = ::hideDialog
                )
            )
        }
    }

    private fun onBlock() {
        screenModelScope.launch {
            friendRepository.blockFriend(targetId)
                .onSuccess {
                    mutableState.value = state.value.copy(
                        stranger = state.value.stranger?.copy(blocked = true)
                    )
                }
                .onFailure {
                    showDialog(
                        DialogRequest(
                            title = getString(Res.string.failed_to_block_friend),
                            description = getString(Res.string.failed_to_block_friend_desc),
                            actionTextRes = Res.string.confirm,
                            onAction = ::hideDialog
                        )
                    )
                }
        }
    }

    fun unblock() {
        screenModelScope.launch {
            friendRepository.unblockFriend(targetId)
                .onSuccess {
                    mutableState.value = state.value.copy(
                        stranger = state.value.stranger?.copy(blocked = false)
                    )
                }
                .onFailure {
                    showDialog(
                        DialogRequest(
                            title = getString(Res.string.failed_to_unblock_friend),
                            description = getString(Res.string.failed_to_unblock_friend_desc),
                            actionTextRes = Res.string.confirm,
                            onAction = ::hideDialog
                        )
                    )
                }
        }
    }

    fun showDialog(request: DialogRequest) {
        mutableState.value = state.value.copy(dialogRequest = request)
    }

    fun hideDialog() {
        mutableState.value = state.value.copy(dialogRequest = null)
    }
}
