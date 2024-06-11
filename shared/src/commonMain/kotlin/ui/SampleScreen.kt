package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mmk.kmpnotifier.notification.NotifierManager
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import team.capybara.moime.SharedRes

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SampleScreen() {
    var fcmToken by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val pushNotifier = NotifierManager.getPushNotifier()
    val webViewState = rememberWebViewState("https://naver.com")

    Scaffold(
        bottomBar = {
            Button(
                modifier = Modifier.fillMaxWidth().height(56.dp),
                onClick = {
                    coroutineScope.launch {
                        fcmToken = pushNotifier.getToken() ?: "cannot get fcm token"
                        print(fcmToken)
                    }
                }
            ) {
                Text(
                    text = fcmToken.takeIf { it.isNotBlank() } ?: "Tap to get FCM Token",
                    fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_bold)
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(painter = painterResource(SharedRes.images.ic_add), contentDescription = null)
            }
        }
    ) { innerPadding ->
        Column(Modifier.fillMaxSize().padding(innerPadding)) {
            WebView(
                state = webViewState,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

