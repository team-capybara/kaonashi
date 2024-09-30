package ui.jsbridge

import com.multiplatform.webview.jsbridge.IJsMessageHandler
import com.multiplatform.webview.jsbridge.JsMessage
import com.multiplatform.webview.web.WebViewNavigator
import kotlinx.serialization.Serializable

class ImagePickerHandler(
    private val onHandle: ((callback: (String) -> Unit) -> Unit)
) : IJsMessageHandler {

    override fun handle(
        message: JsMessage,
        navigator: WebViewNavigator?,
        callback: (String) -> Unit
    ) {
        onHandle(callback)
    }

    override fun methodName(): String = METHOD_NAME

    companion object {
        private const val METHOD_NAME = "onPickImage"
    }
}

@Serializable
data class ImagePickerResponse(
    val image: String
)
