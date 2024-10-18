package ui.main.insight

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.DateTimePeriod
import ui.LocalScreenSize
import ui.theme.Gray600
import ui.theme.Gray800
import ui.util.DateUtil.formatToString
import ui.util.DateUtil.toSeconds

@Composable
fun InsightSummaryCardTime(
    period: DateTimePeriod,
    expanded: Boolean,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val screenSize = LocalScreenSize.current
    val expandedWidth = with(density) { screenSize.width.toDp() - 32.dp }
    val animatedWidth = animateDpAsState(
        if (expanded) expandedWidth else 132.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val animatedTopPadding = animateDpAsState(
        if (expanded) 156.dp else 16.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val expandedProgress = period.toSeconds() / DateTimePeriod(hours = 24).toSeconds().toFloat()
    val animatedProgress = animateFloatAsState(
        if (expanded) expandedProgress else 0f,
    )

    Box(
        modifier = modifier.then(
            Modifier
                .padding(top = animatedTopPadding.value, end = 16.dp)
                .width(animatedWidth.value)
                .aspectRatio(1f)
        ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = expanded
        ) {
            if (expanded) {
                CircularProgressIndicator(
                    progress = { animatedProgress.value },
                    strokeWidth = 16.dp,
                    color = Gray800,
                    trackColor = Gray800.copy(alpha = 0.2f),
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = period.formatToString(),
                    color = Gray800,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 48.sp
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Gray600, shape = CircleShape)
                )
            }
        }
    }
}