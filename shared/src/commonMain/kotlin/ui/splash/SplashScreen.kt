package ui.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.login.LoginScreen
import ui.main.MainScreen

class SplashScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<SplashScreenModel>()
        val state by screenModel.state.collectAsState()

        LaunchedEffect(state) {
            when (state) {
                SplashScreenModel.State.Authorized -> {
                    navigator.replace(MainScreen())
                }

                SplashScreenModel.State.Unauthorized -> {
                    navigator.replace(LoginScreen())
                }

                else -> {}
            }
        }
    }
}
