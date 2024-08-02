package ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import ui.LocalScreenSize
import ui.main.insight.InsightSummaryType
import ui.theme.Gray50
import ui.theme.Gray500
import ui.theme.Gray900

@Composable
fun InsightSummaryCard(
    type: InsightSummaryType,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    val expandedTopPadding = with(density) {
            WindowInsets.statusBars.getTop(this).toDp()
        } + HOME_TOP_APP_BAR_HEIGHT + 40.dp
    val expandedBottomPadding = with(density) {
            WindowInsets.navigationBars.getBottom(this).toDp()
        } + BOTTOM_NAV_BAR_HEIGHT + 42.dp
    val expandedHeight = with(density) {
        LocalScreenSize.current.height.toDp()
    } - expandedTopPadding - expandedBottomPadding

    val animatedHeight = animateDpAsState(
        if (expanded) expandedHeight else INSIGHT_SUMMARY_CARD_HEIGHT,
    )
    val animatedTextColor = animateColorAsState(
        if (expanded) Gray900 else Gray50
    )

    Card(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .animateContentSize()
                .height(animatedHeight.value)
        ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Gray500, contentColor = Gray50)
    ) {
    }
}

private val INSIGHT_SUMMARY_CARD_HEIGHT = 164.dp
