package ui.main.insight

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.stringResource
import team.capybara.moime.SharedRes
import ui.LocalScreenSize
import ui.component.BOTTOM_NAV_BAR_HEIGHT
import ui.component.HOME_TOP_APP_BAR_HEIGHT
import ui.model.dummyMeetings
import ui.model.dummyUsers
import ui.theme.Gray400
import ui.theme.Gray50
import ui.theme.Gray500
import ui.theme.Gray900
import ui.theme.MoimeGreen

@Composable
fun InsightSummaryCard(
    type: InsightSummaryType,
    modifier: Modifier = Modifier,
    onExpandCallback: (Boolean) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    val expandedTopPadding = with(density) {
        WindowInsets.statusBars.getTop(this).toDp()
    } + HOME_TOP_APP_BAR_HEIGHT + 40.dp
    val expandedBottomPadding = with(density) {
        WindowInsets.navigationBars.getBottom(this).toDp()
    } + BOTTOM_NAV_BAR_HEIGHT
    val expandedHeight = with(density) {
        LocalScreenSize.current.height.toDp()
    } - expandedTopPadding - expandedBottomPadding
    val animatedHeight = animateDpAsState(
        if (expanded) expandedHeight else INSIGHT_SUMMARY_CARD_HEIGHT,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )
    val animatedContainerColor = animateColorAsState(
        if (expanded) MoimeGreen else Gray500
    )

    Card(
        modifier = modifier.then(Modifier.fillMaxWidth()),
        onClick = {
            expanded = expanded.not()
            onExpandCallback(expanded)
        },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = animatedContainerColor.value)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(animatedHeight.value)
        ) {
            InsightSummaryCardTitle(
                type = type,
                expanded = expanded,
                modifier = Modifier.padding(16.dp).align(Alignment.TopStart)
            )
            when (type) {
                InsightSummaryType.Friend -> {
                    InsightSummaryCardFriend(
                        expanded = expanded,
                        modifier = Modifier.align(Alignment.TopEnd),
                        users = dummyUsers
                    )
                }

                InsightSummaryType.Meeting -> {
                    InsightSummaryCardMeeting(
                        expanded = expanded,
                        parentHeight = expandedHeight.value,
                        modifier = Modifier.align(Alignment.TopEnd),
                        meetings = dummyMeetings
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun InsightSummaryCardTitle(
    type: InsightSummaryType,
    expanded: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedTitleColor = animateColorAsState(
        if (expanded) Gray900 else Gray50,
        animationSpec = tween(500)
    )
    val animatedTitleSize = animateFloatAsState(
        if (expanded) 32f else 24f,
        animationSpec = tween(500)
    )
    val animatedTitleLineHeight = animateFloatAsState(
        if (expanded) 38.19f else 30f,
        animationSpec = tween(500)
    )
    val animatedPeriodTextColor = animateColorAsState(
        if (expanded) Gray900 else Gray400,
        animationSpec = tween(500)
    )
    val animatedPeriodTextSize = animateFloatAsState(
        if (expanded) 16f else 12f,
        animationSpec = tween(500)
    )

    Column(modifier = modifier) {
        Text(
            text = stringResource(type.titleRes),
            color = animatedTitleColor.value,
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_bold),
            fontSize = animatedTitleSize.value.sp,
            lineHeight = animatedTitleLineHeight.value.sp
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "7월 14일 - 7월 24일",
            color = animatedPeriodTextColor.value,
            fontFamily = if (expanded) {
                fontFamilyResource(SharedRes.fonts.pretendard_semibold)
            } else {
                fontFamilyResource(SharedRes.fonts.pretendard_medium)
            },
            fontSize = animatedPeriodTextSize.value.sp
        )
    }
}

val INSIGHT_SUMMARY_CARD_HEIGHT = 168.dp
