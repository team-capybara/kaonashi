package ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.fontFamilyResource
import team.capybara.moime.SharedRes
import ui.theme.Gray400
import ui.theme.Gray50
import ui.theme.Gray600
import ui.theme.Gray800

@Composable
fun <T : TabView> TabViewSegmentedButtonBar(
    modifier: Modifier = Modifier,
    tabViews: List<T>,
    selected: T,
    onTabViewChanged: (T) -> Unit
) {
    Surface(
        modifier = modifier.then(Modifier.height(SEGMENTED_BUTTON_BAR_HEIGHT)),
        shape = RoundedCornerShape(100.dp),
        color = Gray800
    ) {
        Row(
            modifier = Modifier.fillMaxHeight().padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            tabViews.forEach {
                TabViewSegmentedButton(
                    enabled = selected != it,
                    text = it.getTitleString(),
                    onClick = { onTabViewChanged(it) }
                )
            }
        }
    }
}

@Composable
private fun TabViewSegmentedButton(
    enabled: Boolean,
    text: String,
    onClick: (() -> Unit)?
) {
    Button(
        onClick = onClick ?: {},
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = Gray600,
            disabledContentColor = Gray50,
            containerColor = Gray800,
            contentColor = Gray400
        ),
        shape = RoundedCornerShape(100.dp),
        enabled = enabled
    ) {
        Text(
            text = text,
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
            fontSize = 12.sp
        )
    }
}

private val SEGMENTED_BUTTON_BAR_HEIGHT = 44.dp