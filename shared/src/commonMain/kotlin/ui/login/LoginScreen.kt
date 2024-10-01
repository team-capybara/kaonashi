package ui.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.multiplatform.webview.jsbridge.rememberWebViewJsBridge
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ui.component.MoimeImagePicker
import ui.component.SafeAreaColumn
import ui.jsbridge.ImagePickerResponse
import ui.main.MainScreen
import ui.onboarding.OnboardingScreen
import ui.util.Base64Util.encodeToBase64

class LoginScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
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
                    navigator.replace(if (state.isNewbie) OnboardingScreen() else MainScreen())
                }

                is LoginScreenModel.State.Failure -> {
                    // fail to login
                }
            }
        }

        SafeAreaColumn {
            WebView(
                state = webviewState,
                webViewJsBridge = jsBridge,
                modifier = Modifier.fillMaxSize()
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
