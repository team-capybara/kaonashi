package ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.stringResource
import team.capybara.moime.SharedRes
import ui.model.Meeting
import ui.theme.Gray50
import ui.theme.Gray800

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeetingsBottomSheet(
    meetings: List<Meeting>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        contentColor = Gray800
    ) {
        Column(
            modifier = modifier.then(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp)
            )
        ) {
            Text(
                text = stringResource(SharedRes.strings.today_meetings),
                fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
                fontSize = 16.sp,
                color = Gray50
            )
            Spacer(Modifier.height(12.dp))
            meetings.forEach {
                MoimeMeetingCard(
                    meeting = it,
                    isAnotherTodayMeetingCardFocusing = false,
                    forceDefaultHeightStyle = true,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}
