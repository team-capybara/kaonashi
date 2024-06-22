package team.capybara.moime

import App
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.mmk.kmpnotifier.extensions.onCreateOrOnNewIntent
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.permission.permissionUtil

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSystemBarStyle()
        initNotifier()
        setContent {
            App()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        NotifierManager.onCreateOrOnNewIntent(intent)
    }

    private fun setSystemBarStyle() {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                TOP_APP_BAR_COLOR.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.dark(
                BOTTOM_NAV_BAR_COLOR.toArgb()
            )
        )
    }

    private fun initNotifier() {
        val notificationPermissionUtil by permissionUtil()
        notificationPermissionUtil.askNotificationPermission()

        NotifierManager.onCreateOrOnNewIntent(intent)
    }

    companion object {
        private val TOP_APP_BAR_COLOR = Color(0xE5292929)
        private val BOTTOM_NAV_BAR_COLOR = Color(0xD91E1E1E)
    }
}
