package ui.meeting.detail

import cafe.adriel.voyager.core.model.StateScreenModel
import com.multiplatform.webview.jsbridge.IJsMessageHandler
import com.multiplatform.webview.jsbridge.JsMessage
import com.multiplatform.webview.web.WebViewNavigator
import ui.model.Meeting

class DetailScreenModel(meeting: Meeting) :
    StateScreenModel<DetailScreenModel.State>(State.Init) {

    sealed interface State {
        data object Init : State
        data object NavigateToCamera : State
    }

    val webViewUrl = "https://www.moime.app/" + when (meeting.status) {
        Meeting.Status.Created -> "upcoming-gathering"
        Meeting.Status.Ongoing -> "ongoing-gathering"
        Meeting.Status.Finished -> "ended-gathering"
        else -> ""
    } + "?moimId=${meeting.id}"

    inner class CameraJsMessageHandler : IJsMessageHandler {

        override fun handle(
            message: JsMessage,
            navigator: WebViewNavigator?,
            callback: (String) -> Unit
        ) {
            mutableState.value = State.NavigateToCamera
        }

        override fun methodName(): String = BRIDGE_CAMERA_NAVIGATION_METHOD_NAME
    }

    fun initState() {
        mutableState.value = State.Init
    }

    companion object {
        private const val BRIDGE_CAMERA_NAVIGATION_METHOD_NAME = "onNavigateCamera"
    }
}
