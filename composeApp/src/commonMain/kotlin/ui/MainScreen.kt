package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun MainScreen() {
    val webViewState = rememberWebViewState("https://naver.com")

    Scaffold(
        bottomBar = {
            Surface(Modifier.fillMaxWidth().height(56.dp)) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "대충 BottomNavigationBar")
                }
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
