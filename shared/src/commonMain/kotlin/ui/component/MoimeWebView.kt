package ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.multiplatform.webview.cookie.Cookie
import com.multiplatform.webview.jsbridge.IJsMessageHandler
import com.multiplatform.webview.jsbridge.rememberWebViewJsBridge
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState

@Composable
fun MoimeWebView(
    url: String,
    accessToken: String?,
    jsMessageHandlers: List<IJsMessageHandler> = emptyList(),
    modifier: Modifier = Modifier
) {
    val webViewState = rememberWebViewState(url)
    val jsBridge = rememberWebViewJsBridge()

    LaunchedEffect(webViewState) {
        accessToken?.let {
            webViewState.cookieManager.setCookie(
                url = url,
                cookie = Cookie(
                    name = "Authorization",
                    value = "Bearer $it"
                )
            )
        }
    }

    LaunchedEffect(jsBridge) {
        with(jsBridge) {
            jsMessageHandlers.forEach { register(it) }
        }
    }

    SafeAreaColumn {
        Box {
            WebView(
                state = webViewState,
                webViewJsBridge = jsBridge,
                modifier = modifier.then(Modifier.fillMaxSize())
            )
            if (webViewState.isLoading) {
                MoimeLoading()
            }
        }
    }
}
