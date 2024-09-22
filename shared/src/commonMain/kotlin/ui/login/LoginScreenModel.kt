package ui.login

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.multiplatform.webview.jsbridge.IJsMessageHandler
import com.multiplatform.webview.jsbridge.JsMessage
import com.multiplatform.webview.jsbridge.dataToJsonString
import com.multiplatform.webview.jsbridge.processParams
import com.multiplatform.webview.web.WebViewNavigator
import kotlinx.coroutines.launch
import ui.repository.UserRepository

class LoginScreenModel(
    private val userRepository: UserRepository
) : StateScreenModel<LoginScreenModel.State>(State.Init) {

    sealed interface State {
        data object Init : State
        data class Success(val isNewbie: Boolean) : State
        data class Failure(val throwable: Throwable?) : State
    }

    inner class LoginJsMessageHandler : IJsMessageHandler {

        override fun handle(
            message: JsMessage,
            navigator: WebViewNavigator?,
            callback: (String) -> Unit
        ) {
            val loginJsMessage = processParams<LoginJsMessage>(message)
            loginJsMessage.accessToken.takeIf { it.isNotBlank() }?.let { accessToken ->
                screenModelScope.launch {
                    userRepository.login(accessToken)
                        .onSuccess { fcmToken ->
                            mutableState.value = State.Success(isNewbie = loginJsMessage.isNewbie)
                            fcmToken?.let { (dataToJsonString(LoginJsCallback(it))) }
                        }
                        .onFailure { State.Failure(it) }
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
