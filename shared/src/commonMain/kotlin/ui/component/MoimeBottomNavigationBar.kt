package ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
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
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.hazeChild
import moime.shared.generated.resources.Res
import moime.shared.generated.resources.ic_add
import org.jetbrains.compose.resources.painterResource
import ui.LocalHazeState
import ui.main.home.HomeTab
import ui.main.insight.InsightTab

@Composable
fun MoimeBottomNavigationBar(
    onAction: () -> Unit
) {
    val tabNavigator = LocalTabNavigator.current
    val hazeState = LocalHazeState.current
    Surface(
        color = BACKGROUND_COLOR,
        modifier = Modifier
            .hazeChild(
                state = hazeState,
                style = HazeDefaults.style(
                    blurRadius = 16.dp
                )
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .height(BOTTOM_NAV_BAR_HEIGHT)
        ) {
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
                    .padding(top = 10.dp, bottom = 28.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(58f))
                HomeTab.toIconButton(tabNavigator = tabNavigator)
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
                        .clickable { onAction() }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_add),
                            modifier = Modifier.size(24.dp),
                            tint = Color(0xFF292929),
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(70f))
                InsightTab.toIconButton(tabNavigator = tabNavigator)
                Spacer(modifier = Modifier.weight(58f))
            }
        }
    }
}

@Composable
private fun Tab.toIconButton(
    modifier: Modifier = Modifier,
    tabNavigator: TabNavigator
) =
    IconButton(
        onClick = { tabNavigator.current = this }
    ) {
        Icon(
            painter = requireNotNull(options.icon),
            contentDescription = options.title,
            modifier = modifier.then(Modifier.size(24.dp)),
            tint = ICON_COLOR.copy(alpha = if (tabNavigator.current == this) 1f else 0.3f)
        )
    }

val BOTTOM_NAV_BAR_HEIGHT = 94.dp
private val BACKGROUND_COLOR = Color(0xD91E1E1E)
private val BORDER_COLOR = Color(0x1AFFFFFF)
private val ACTION_BUTTON_COLOR = Color(0xFF00E86B)
private val ICON_COLOR = Color(0xFFFFFFFF)
