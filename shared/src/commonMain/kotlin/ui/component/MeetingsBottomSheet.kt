package ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.model.Meeting
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
