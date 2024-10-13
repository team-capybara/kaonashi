package ui.mypage

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.multiplatform.webview.jsbridge.IJsMessageHandler
import com.multiplatform.webview.jsbridge.JsMessage
import com.multiplatform.webview.jsbridge.dataToJsonString
import com.multiplatform.webview.web.WebViewNavigator
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.launch
import ui.jsbridge.ImagePickerHandler

class MyPageScreenModel(
    private val permissionsController: PermissionsController
) : StateScreenModel<MyPageScreenModel.State>(State()) {

    data class State(
        val onImagePicked: ((String) -> Unit)? = null
    )

    val imagePickerHandler = ImagePickerHandler { callback ->
        mutableState.value = state.value.copy(onImagePicked = callback)
    }

    inner class MyPageJsMessageHandler : IJsMessageHandler {

        override fun handle(
            message: JsMessage,
            navigator: WebViewNavigator?,
            callback: (String) -> Unit
        ) {
            screenModelScope.launch {
                val jsCallbackResponse = MyPageJsCallback(
                    granted = permissionsController.isPermissionGranted(Permission.REMOTE_NOTIFICATION)
                )
                callback(dataToJsonString(jsCallbackResponse))
            }
        }

        override fun methodName(): String = BRIDGE_MY_PAGE_METHOD_NAME
    }

    companion object {
        internal const val WEBVIEW_URL_PATH_MY_PAGE = "mypage"
        internal const val BRIDGE_MY_PAGE_METHOD_NAME = "onGetNotificationPermission"
    }
}
