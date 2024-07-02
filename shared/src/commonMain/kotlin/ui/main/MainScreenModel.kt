package ui.main

import cafe.adriel.voyager.core.model.StateScreenModel
import ui.model.Meeting

class MainScreenModel : StateScreenModel<MainScreenModel.State>(State.Init) {

    sealed class State {
        data object Init : State()
        data class ShowMeetingsBottomSheet(val meetings: List<Meeting>) : State()
    }

    fun showMeetingsBottomSheet(meetings: List<Meeting>) {
        println("bottom sheet showing: $meetings")
        mutableState.value = State.ShowMeetingsBottomSheet(meetings)
    }

    fun hideMeetingsBottomSheet() {
        mutableState.value = State.Init
    }
}
