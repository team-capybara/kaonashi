package ui.login

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.mmk.kmpnotifier.notification.NotifierManager
import com.multiplatform.webview.jsbridge.IJsMessageHandler
import com.multiplatform.webview.jsbridge.JsMessage
import com.multiplatform.webview.jsbridge.dataToJsonString
import com.multiplatform.webview.jsbridge.processParams
import com.multiplatform.webview.web.WebViewNavigator
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.plugin
import io.ktor.client.request.get
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginScreenModel: StateScreenModel<LoginScreenModel.State>(State.Init), KoinComponent {
    private val httpClient: HttpClient by inject()
    private val settings: Settings by inject()
    private val notifier = NotifierManager.getPushNotifier()

    sealed interface State {
        data object Init: State
        data class Success(val isNewbie: Boolean): State
        data class Failure(val throwable: Throwable?): State
    }

    inner class LoginJsMessageHandler : IJsMessageHandler {

        override fun handle(
            message: JsMessage,
            navigator: WebViewNavigator?,
            callback: (String) -> Unit
        ) {
            val loginJsMessage = processParams<LoginJsMessage>(message)
            loginJsMessage.accessToken.takeIf { it.isNotBlank() }?.let { accessToken ->
                settings.putString("accessToken", accessToken)
                mutableState.value = State.Success(isNewbie = loginJsMessage.isNewbie)
                screenModelScope.launch {
                    httpClient
                        .plugin(Auth).providers
                        .filterIsInstance<BearerAuthProvider>().firstOrNull()
                        ?.refreshToken(httpClient.get(LOGIN_URL))
                    notifier.getToken()?.let { fcmToken ->
                        callback(dataToJsonString(LoginJsCallback(fcmToken)))
                    }
                }
            } ?: run {
                mutableState.value = State.Failure(Throwable("AccessToken is blank."))
            }
        }

        override fun methodName(): String = "onLoginSuccess"
    }

    companion object {
        const val LOGIN_URL = "https://www.moime.app/"
    }
}
