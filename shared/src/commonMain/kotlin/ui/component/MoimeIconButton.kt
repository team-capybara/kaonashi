package ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import ui.theme.Gray50

@Composable
fun MoimeIconButton(
    iconRes: ImageResource,
    tint: Color = Gray50,
    size: Dp = 24.dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
        IconButton(
            onClick = onClick,
            modifier = modifier,
        ) {
            Icon(
                painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(size),
                tint = tint
            )
        }
    }
}
