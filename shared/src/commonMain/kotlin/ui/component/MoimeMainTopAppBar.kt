package ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.hazeChild
import dev.icerock.moko.resources.compose.painterResource
import team.capybara.moime.SharedRes
import ui.LocalHazeState
import ui.main.MainTab
import ui.main.MainTabView
import ui.main.home.HomeTabView

@Composable
fun MoimeMainTopAppBar(
    profileImageUrl: String,
    currentTab: MainTab,
    currentTabView: MainTabView,
    onClickUserAdd: () -> Unit,
    onClickNotification: () -> Unit,
    onClickFirstTabView: () -> Unit,
    onClickSecondTabView: () -> Unit,
    hiddenBackground: Boolean,
) {
    val hazeState = LocalHazeState.current
    val animatedColor = animateColorAsState(
        if (hiddenBackground && currentTabView == HomeTabView.ListView) {
            Color.Transparent
        } else {
            BACKGROUND_COLOR
        }
    )
    val animatedBlur = animateDpAsState(
        if (hiddenBackground && currentTabView == HomeTabView.ListView) {
            (0.000001).dp
        } else {
            16.dp
        }
    )

    Surface(
        color = animatedColor.value,
        modifier = Modifier
            .then(
                if (!hiddenBackground || currentTabView == HomeTabView.CalendarView) {
                    Modifier.hazeChild(
                        state = hazeState,
                        style = HazeDefaults.style(
                            blurRadius = animatedBlur.value
                        )
                    )
                } else {
                    Modifier
                }
            )
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
                .height(HOME_TOP_APP_BAR_HEIGHT)
                .padding(top = 10.dp, bottom = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .safeDrawingPadding()
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MoimeProfileImage(profileImageUrl, size = 36.dp, enableBorder = false)
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onClickUserAdd) {
                    Icon(
                        painter = painterResource(SharedRes.images.ic_user_add),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(onClick = onClickNotification) {
                    Icon(
                        painter = painterResource(SharedRes.images.ic_notification),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            MainTabViewSegmentedButtonBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                tabViews = currentTab.tabViews,
                selected = currentTabView,
                onClickFirstTabView = onClickFirstTabView,
                onClickSecondTabView = onClickSecondTabView
            )
        }
    }
}

val HOME_TOP_APP_BAR_HEIGHT = 136.dp
private val BACKGROUND_COLOR = Color(0xE5292929)