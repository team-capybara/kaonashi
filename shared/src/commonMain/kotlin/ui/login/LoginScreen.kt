package ui.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.multiplatform.webview.jsbridge.rememberWebViewJsBridge
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import ui.main.MainScreen
import ui.onboarding.OnboardingScreen

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel { LoginScreenModel() }
        val webviewState = rememberWebViewState(LoginScreenModel.LOGIN_URL)
        val jsBridge = rememberWebViewJsBridge()
        val loginState by screenModel.state.collectAsState()

        LaunchedEffect(jsBridge) {
            jsBridge.register(screenModel.LoginJsMessageHandler())
        }

        LaunchedEffect(loginState) {
            when(loginState) {
                is LoginScreenModel.State.Success -> {
                    val isNewbie = (loginState as LoginScreenModel.State.Success).isNewbie
                    navigator.replace(if (isNewbie) OnboardingScreen() else MainScreen())
                }
                is LoginScreenModel.State.Failure -> {
                    // fail to login
                }
                LoginScreenModel.State.Init -> {}
            }
        }

        WebView(
            state = webviewState,
            webViewJsBridge = jsBridge,
            modifier = Modifier.fillMaxSize()
        )
    }
}
