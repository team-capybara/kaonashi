package ui.meeting.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.russhwolf.settings.Settings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.component.MoimeWebView
import ui.jsbridge.ACCESS_TOKEN_KEY
import ui.jsbridge.PopHandler
import ui.meeting.camera.CameraScreen
import ui.model.Meeting

data class MeetingScreen(private val meeting: Meeting) : Screen, KoinComponent {

    private val settings: Settings by inject()
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = rememberScreenModel { MeetingScreenModel(meeting) }
        val popHandler = PopHandler { navigator.pop() }
        val state by screenModel.state.collectAsState()

        LaunchedEffect(state) {
            if (state is MeetingScreenModel.State.NavigateToCamera) {
                navigator.push(CameraScreen(meeting.id))
                screenModel.initState()
            }
        }

        MoimeWebView(
            url = screenModel.webViewUrl,
            accessToken = settings.getString(ACCESS_TOKEN_KEY, ""),
            jsMessageHandlers = listOf(screenModel.CameraJsMessageHandler(), popHandler)
        )
    }
}
