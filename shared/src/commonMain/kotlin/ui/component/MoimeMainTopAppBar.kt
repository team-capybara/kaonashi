package ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import dev.icerock.moko.resources.compose.painterResource
import team.capybara.moime.SharedRes
import ui.main.tab.home.HomeTabView
import ui.theme.Gray200

@Composable
fun MoimeMainTopAppBar(
    hazeState: HazeState,
    profileImageUrl: String,
    selectedTabView: HomeTabView,
    onClickUserAdd: () -> Unit,
    onClickNotification: () -> Unit,
    onClickListView: () -> Unit,
    onClickCalendarView: () -> Unit
) {
    Surface(
        color = BACKGROUND_COLOR,
        modifier = Modifier
            .hazeChild(
                state = hazeState,
                style = HazeDefaults.style(
                    blurRadius = 8.dp
                )
            )
            .fillMaxWidth()
            .height(HEIGHT)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 36.dp, bottom = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .padding(start = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CoilImage(
                    imageModel = { profileImageUrl },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center
                    ),
                    loading = {
                        Surface(
                            shape = CircleShape,
                            color = Gray200,
                            modifier = Modifier.size(36.dp)
                        ) {}
                    },
                    modifier = Modifier.size(36.dp).clip(CircleShape)
                )
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
            HomeTabViewSegmentedButtonBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                selected = selectedTabView,
                onClickListView = onClickListView,
                onClickCalendarView = onClickCalendarView
            )
        }
    }
}

private val HEIGHT = 186.dp
private val BACKGROUND_COLOR = Color(0xD9292929)