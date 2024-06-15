package ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import dev.icerock.moko.resources.compose.painterResource
import team.capybara.moime.SharedRes
import ui.main.tab.HomeTab
import ui.main.tab.MainTab
import ui.main.tab.StatisticsTab

@Composable
fun MoimeBottomNavigationBar(
    hazeState: HazeState
) {
    val tabNavigator = LocalTabNavigator.current

    Surface(
        color = BACKGROUND_COLOR,
        modifier = Modifier
            .hazeChild(
                state = hazeState,
                style = HazeDefaults.style(
                    backgroundColor = BACKGROUND_COLOR,
                    blurRadius = 8.dp
                )
            )
            .fillMaxWidth()
            .height(HEIGHT)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(),
                color = BORDER_COLOR,
                thickness = 1.dp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .hazeChild(
                        state = hazeState,
                        style = HazeDefaults.style(
                            blurRadius = 16.dp
                        )
                    )
                    .padding(top = 10.dp, bottom = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(58f))
                HomeTab(hazeState).toIconButton(tabNavigator = tabNavigator)
                Spacer(modifier = Modifier.weight(70f))
                Surface(
                    color = ACTION_BUTTON_COLOR,
                    shape = CircleShape,
                    modifier = Modifier
                        .size(56.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = CircleShape,
                            ambientColor = ACTION_BUTTON_COLOR,
                            spotColor = ACTION_BUTTON_COLOR
                        )
                        .clickable { }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(SharedRes.images.ic_add),
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFF292929),
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(70f))
                StatisticsTab(hazeState).toIconButton(tabNavigator = tabNavigator)
                Spacer(modifier = Modifier.weight(58f))
            }
        }
    }
}

@Composable
private fun MainTab.toIconButton(
    modifier: Modifier = Modifier,
    tabNavigator: TabNavigator
) =
    IconButton(
        onClick = { tabNavigator.current = this }
    ) {
        Icon(
            painter = painterResource(iconResource),
            contentDescription = options.title,
            modifier = modifier.then(Modifier.size(24.dp)),
            tint = ICON_COLOR.copy(alpha = if (tabNavigator.current == this) 1f else 0.3f)
        )
    }

private val HEIGHT = 98.dp
private val BACKGROUND_COLOR = Color(0xD91E1E1E)
private val BORDER_COLOR = Color(0x1AFFFFFF)
private val ACTION_BUTTON_COLOR = Color(0xFF00E86B)
private val ICON_COLOR = Color(0xFFFFFFFF)
