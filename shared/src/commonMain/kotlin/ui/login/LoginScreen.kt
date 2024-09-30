package ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.multiplatform.webview.jsbridge.rememberWebViewJsBridge
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ui.component.MoimeImagePicker
import ui.jsbridge.ImagePickerResponse
import ui.main.MainScreen
import ui.onboarding.OnboardingScreen
import ui.theme.Gray700
import ui.util.Base64Util.encodeToBase64

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val density = LocalDensity.current
        val navigator = LocalNavigator.currentOrThrow
        val coroutineScope = rememberCoroutineScope()
        val screenModel = koinScreenModel<LoginScreenModel>()
        val webviewState = rememberWebViewState(LoginScreenModel.WEBVIEW_LOGIN_URL)
        val jsBridge = rememberWebViewJsBridge()
        val loginState by screenModel.state.collectAsState()

        LaunchedEffect(jsBridge) {
            with(jsBridge) {
                register(screenModel.LoginJsMessageHandler())
                register(screenModel.imagePickerHandler)
            }
        }

        LaunchedEffect(loginState) {
            when (val state = loginState) {
                is LoginScreenModel.State.InProgress -> {
                }

                is LoginScreenModel.State.Success -> {
                    val isNewbie = state.isNewbie
                    navigator.replace(if (isNewbie) OnboardingScreen() else MainScreen())
                }

                is LoginScreenModel.State.Failure -> {
                    // fail to login
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().background(color = Gray700)
        ) {
            WebView(
                state = webviewState,
                webViewJsBridge = jsBridge,
                modifier = Modifier.padding(
                    top = with(density) { WindowInsets.statusBars.getTop(this).toDp() }
                ).fillMaxSize()
            )
        }

        (loginState as? LoginScreenModel.State.InProgress)?.onImagePicked?.let { callback ->
            MoimeImagePicker(onPicked = { images ->
                images.firstOrNull()?.let {
                    callback(Json.encodeToString(ImagePickerResponse(it.encodeToBase64())))
                }
            })
        }
    }
}
