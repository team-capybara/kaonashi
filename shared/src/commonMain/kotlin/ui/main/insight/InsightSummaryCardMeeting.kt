package ui.main.insight

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.unit.sp
import kotlinx.datetime.DayOfWeek
import ui.LocalScreenSize
import ui.theme.Gray600
import ui.theme.Gray800
import ui.util.DateUtil.toKr

@Composable
fun InsightSummaryCardMeeting(
    expanded: Boolean,
    parentHeight: Float,
    meetingsCount: Map<DayOfWeek, Int>,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val screenSize = LocalScreenSize.current
    val expandedWidth = with(density) { screenSize.width.toDp() - 64.dp }
    val animatedWidth = animateDpAsState(
        if (expanded) expandedWidth else 136.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val animatedHeight = animateDpAsState(
        if (expanded) {
            (parentHeight / 512 * 290).dp
        } else {
            INSIGHT_SUMMARY_CARD_HEIGHT - 32.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val animatedColor = animateColorAsState(
        if (expanded) Gray800.copy(alpha = 0.2f) else Gray600,
        animationSpec = tween(500)
    )
    val animatedPadding = animateDpAsState(
        if (expanded) 9.dp else 4.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val animatedRadius = animateDpAsState(
        if (expanded) 24.dp else 12.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val defaultXPadding = with(density) { 16.dp.toPx() }
    val pxToMoveDown = with(density) { (parentHeight / 512 * 176).dp.toPx() }
    val animatedOffset = animateOffsetAsState(
        if (expanded) {
            Offset(-defaultXPadding, pxToMoveDown)
        } else {
            Offset(-defaultXPadding, defaultXPadding)
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val animatedDayOfWeekHeight = animateDpAsState(
        if (expanded) 30.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Column(
        modifier = modifier.then(
            Modifier.offset { animatedOffset.value.round() }
        )
    ) {
        AnimatedVisibility(
            expanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .width(animatedWidth.value)
                    .height(animatedDayOfWeekHeight.value)
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DayOfWeek.entries.forEach {
                    Text(
                        text = it.toKr(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Gray800.copy(alpha = 0.2f)
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .width(animatedWidth.value)
                .height(animatedHeight.value),
            horizontalArrangement = Arrangement.spacedBy(animatedPadding.value)
        ) {
            DayOfWeek.entries.forEach { dayOfWeek ->
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    color = animatedColor.value,
                    shape = RoundedCornerShape(animatedRadius.value)
                ) {
                    AnimatedVisibility(
                        expanded,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            repeat(meetingsCount.getValue(dayOfWeek)) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .background(color = Gray800, shape = CircleShape),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
