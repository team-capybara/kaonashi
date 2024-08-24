package ui.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.multiplatform.webview.jsbridge.rememberWebViewJsBridge
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { LoginScreenModel() }
        val webviewState = rememberWebViewState(LoginScreenModel.LOGIN_URL)
        val jsBridge = rememberWebViewJsBridge()
        val state by screenModel.state.collectAsState()

        LaunchedEffect(jsBridge) {
            jsBridge.register(screenModel.LoginJsMessageHandler())
        }

        WebView(
            state = webviewState,
            webViewJsBridge = jsBridge,
            modifier = Modifier.fillMaxSize()
        )
    }
}
