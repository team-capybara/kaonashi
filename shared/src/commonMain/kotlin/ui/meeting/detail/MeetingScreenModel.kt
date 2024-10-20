package ui.meeting.detail

import cafe.adriel.voyager.core.model.StateScreenModel
import com.multiplatform.webview.jsbridge.IJsMessageHandler
import com.multiplatform.webview.jsbridge.JsMessage
import com.multiplatform.webview.web.WebViewNavigator
import ui.jsbridge.WEBVIEW_BASE_URL
import ui.model.Meeting

class MeetingScreenModel(meeting: Meeting) :
    StateScreenModel<MeetingScreenModel.State>(State.Init) {

    sealed interface State {
        data object Init : State
        data object NavigateToCamera : State
    }

    val webViewUrl = WEBVIEW_BASE_URL + when (meeting.status) {
        Meeting.Status.Created -> "upcoming-gathering"
        Meeting.Status.Ongoing -> "ongoing-gathering"
        Meeting.Status.Finished -> "ended-gathering"
        Meeting.Status.Completed -> "memory-gathering"
        else -> "error"
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
