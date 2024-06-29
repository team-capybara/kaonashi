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
import ui.main.home.HomeTabView
import ui.theme.Gray400
import ui.theme.Gray50
import ui.theme.Gray600
import ui.theme.Gray800

@Composable
fun HomeTabViewSegmentedButtonBar(
    modifier: Modifier = Modifier,
    selected: HomeTabView,
    onClickListView: () -> Unit,
    onClickCalendarView: () -> Unit
) {
    Surface(
        modifier = modifier.then(Modifier.height(HEIGHT)),
        shape = RoundedCornerShape(100.dp),
        color = Gray800
    ) {
        Row(
            modifier = Modifier.fillMaxHeight().padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            HomeTabView.entries.forEach {
                HomeTabViewSegmentedButton(
                    enabled = selected != it,
                    text = it.text,
                    onClick = when (it) {
                        HomeTabView.ListView -> onClickListView
                        HomeTabView.CalendarView -> onClickCalendarView
                    }
                )
            }
        }
    }
}

@Composable
private fun HomeTabViewSegmentedButton(
    enabled: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = Gray600,
            disabledContentColor = Gray50,
            containerColor = Gray800,
            contentColor = Gray400
        ),
        shape = RoundedCornerShape(20.dp),
        enabled = enabled
    ) {
        Text(
            text = text,
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
            fontSize = 12.sp,
            lineHeight = 20.sp
        )
    }
}

private val HEIGHT = 44.dp