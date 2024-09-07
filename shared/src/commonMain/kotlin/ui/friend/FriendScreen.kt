package ui.friend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import team.capybara.moime.SharedRes
import ui.theme.Gray50

class FriendScreen : Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            FriendTopAppBar(
                onClose = {},
                onMore = {}
            )
            FriendAddTitle()
            Spacer(Modifier.height(36.dp))
            FriendInvitation()
        }
    }
}

@Composable
private fun FriendTopAppBar(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    onMore: () -> Unit
) {
    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
        ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onClose) {
            Icon(
                painterResource(SharedRes.images.ic_close),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Gray50
            )
        }
        IconButton(onClick = onMore) {
            Icon(
                painterResource(SharedRes.images.ic_more),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Gray50
            )
        }
    }
}

@Composable
private fun FriendAddTitle(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(SharedRes.strings.add_friend),
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_bold),
            fontSize = 24.sp,
            color = Gray50
        )
        Text(
            text = stringResource(SharedRes.strings.add_friend_desc),
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_medium),
            fontSize = 14.sp,
            color = Gray50
        )
    }
}

@Composable
private fun FriendInvitation(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(SharedRes.strings.invite_app),
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
            fontSize = 16.sp,
            color = Gray50
        )
        Spacer(Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            InvitationType.entries.forEach {
                FriendInvitationButton(
                    imageRes = it.imageResource,
                    textRes = it.stringResource
                )
            }
        }
    }
}

@Composable
private fun FriendInvitationButton(
    modifier: Modifier = Modifier,
    imageRes: ImageResource,
    textRes: StringResource
) {
    Button(
        onClick = {},
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier.size(48.dp).clip(CircleShape)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = stringResource(textRes),
                fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_medium),
                fontSize = 12.sp,
                color = Gray50
            )
        }
    }
}
