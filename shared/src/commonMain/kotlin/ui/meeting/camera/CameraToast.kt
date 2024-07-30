package ui.meeting.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import team.capybara.moime.SharedRes
import ui.theme.Gray200
import ui.theme.Gray50
import ui.theme.Gray700
import ui.theme.MoimeRed

@Composable
fun CameraToast(
    state: CameraToastState
) {
    state.message?.let { message ->
        Box(
            modifier = Modifier
                .background(
                    color = when (state.type) {
                        CameraToastState.Type.Normal -> Gray200
                        CameraToastState.Type.Error -> MoimeRed
                    },
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.padding(
                    horizontal = when (state.type) {
                        CameraToastState.Type.Normal -> 16.dp
                        CameraToastState.Type.Error -> 8.dp
                    },
                    vertical = 8.dp
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (state.type == CameraToastState.Type.Error) {
                    Icon(
                        painterResource(SharedRes.images.ic_info_circle),
                        contentDescription = null,
                        tint = Gray50,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    stringResource(message),
                    fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
                    fontSize = 12.sp,
                    color = when (state.type) {
                        CameraToastState.Type.Normal -> Gray700
                        CameraToastState.Type.Error -> Gray50
                    }
                )
            }
        }
    }
}