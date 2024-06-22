package ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import dev.icerock.moko.resources.compose.fontFamilyResource
import team.capybara.moime.SharedRes
import ui.model.Meeting
import ui.theme.Gray400
import ui.theme.Gray50
import ui.theme.Gray500
import ui.theme.Gray700
import ui.util.DateUtil.getDdayString
import ui.util.DateUtil.getMonthDayString
import ui.util.DateUtil.getTimeString

@Composable
fun MoimeMeetingCard(
    meeting: Meeting,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.then(Modifier.fillMaxWidth().height(100.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Gray500, contentColor = Gray50)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            meeting.thumbnailUrl?.let {
                CoilImage(
                    imageModel = { it },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.colorMatrix(
                            GrayScaleColorMatrix
                        ),
                        alignment = Alignment.Center
                    ),
                    modifier = Modifier.fillMaxSize()
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 15.dp, horizontal = 11.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MoimeProfileImageStack(meeting.participants.map { it.profileImageUrl })
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = meeting.title,
                        fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
                        fontSize = 24.sp,
                        lineHeight = 30.sp,
                        color = Gray50,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "${meeting.date.getMonthDayString()} | ${meeting.date.getTimeString()} | ${meeting.location}",
                        fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_regular),
                        color = Gray400,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Gray700,
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 5.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = meeting.date.getDdayString(),
                            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_regular),
                            fontSize = 14.sp,
                            color = Gray50
                        )
                    }
                }
            }
        }
    }
}

private val HEIGHT = 114.dp
