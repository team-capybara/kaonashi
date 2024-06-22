package ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.fontFamilyResource
import team.capybara.moime.SharedRes
import ui.theme.Gray50
import ui.theme.Gray500

@Composable
fun MoimeProfileImageStack(
    profileImageUrls: List<String>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.then(Modifier.size(50.dp))
    ) {
        when (profileImageUrls.size) {
            0 -> {}

            1 -> {
                MoimeProfileImage(
                    profileImageUrls[0],
                    size = 36.dp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            2 -> {
                MoimeProfileImage(
                    profileImageUrls[0],
                    size = 32.dp,
                    modifier = Modifier.align(Alignment.TopStart)
                )
                MoimeProfileImage(
                    profileImageUrls[1],
                    size = 32.dp,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }

            3 -> {
                MoimeProfileImage(
                    profileImageUrls[0],
                    size = 28.dp,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
                MoimeProfileImage(
                    profileImageUrls[1],
                    size = 28.dp,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
                MoimeProfileImage(
                    profileImageUrls[2],
                    size = 28.dp,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }

            else -> {
                MoimeProfileImage(
                    profileImageUrls[0],
                    size = 25.dp,
                    modifier = Modifier.align(Alignment.TopStart)
                )
                MoimeProfileImage(
                    profileImageUrls[1],
                    size = 25.dp,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
                MoimeProfileImage(
                    profileImageUrls[2],
                    size = 25.dp,
                    modifier = Modifier.align(Alignment.BottomStart)
                )
                Surface(
                    modifier = Modifier.size(25.dp).align(Alignment.BottomEnd),
                    shape = CircleShape,
                    color = Color.Black,
                    border = BorderStroke(1.dp, Gray500)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+${profileImageUrls.size - 3}",
                            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
                            fontSize = 12.sp,
                            color = Gray50
                        )
                    }
                }
            }
        }
    }
}
