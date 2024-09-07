package ui.main.insight

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import ui.LocalScreenSize
import ui.component.MoimeProfileImage
import ui.model.User
import ui.theme.Gray600
import ui.theme.Gray800

@Composable
fun InsightSummaryCardFriend(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    users: List<User>
) {
    val density = LocalDensity.current
    val expandedWidth = with(density) {
        LocalScreenSize.current.width.toDp() - 64.dp
    }
    val animatedWidth = animateDpAsState(
        if (expanded) expandedWidth else 134.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val animatedPadding = animateDpAsState(
        if (expanded) 4.dp else 2.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val defaultPadding = with(density) { 16.dp.toPx() }
    val pxToMoveDown = with(density) { 136.dp.toPx() }
    val animatedOffset = animateOffsetAsState(
        if (expanded) {
            Offset(-defaultPadding, pxToMoveDown)
        } else {
            Offset(-defaultPadding, defaultPadding)
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val (ROW, COL) = run { 5 to 4 }
    Column(
        modifier = modifier.then(
            Modifier
                .width(animatedWidth.value)
                .offset { animatedOffset.value.round() }
        ),
        verticalArrangement = Arrangement.spacedBy(animatedPadding.value)
    ) {
        repeat(ROW) { r ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(animatedPadding.value)
            ) {
                repeat(COL) { c ->
                    val index = COL * r + c
                    InsightSummaryCardFriendItem(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f),
                        parentExpanded = expanded,
                        user = if (index < users.size) users[index] else null
                    )
                }
            }
        }
    }
}

@Composable
private fun InsightSummaryCardFriendItem(
    modifier: Modifier = Modifier,
    parentExpanded: Boolean,
    user: User?
) {
    var flipped by remember { mutableStateOf(false) }
    val animatedRotation = animateFloatAsState(
        if (flipped) 180f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val animatedColor = animateColorAsState(
        if (parentExpanded) Gray800 else Gray600,
        animationSpec = tween(500)
    )
    Surface(
        modifier = modifier.then(
            if (parentExpanded && user != null) {
                Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                try {
                                    flipped = true
                                    awaitRelease()
                                } finally {
                                    flipped = false
                                }
                            }
                        )
                    }
                    .graphicsLayer {
                        rotationY = animatedRotation.value
                    }
            } else Modifier
        ),
        color = animatedColor.value.copy(
            alpha = if (!parentExpanded || user != null) 1f else 0.2f
        ),
        shape = CircleShape
    ) {
        if (parentExpanded && animatedRotation.value >= 90f) {
            MoimeProfileImage(
                imageUrl = requireNotNull(user?.profileImageUrl),
                enableBorder = false,
                placeholderColor = Gray800,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f }
            )
        }
    }
}
