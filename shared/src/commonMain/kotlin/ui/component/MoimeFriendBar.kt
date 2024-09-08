package ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import team.capybara.moime.SharedRes
import ui.model.Friend
import ui.theme.Gray400

@Composable
fun MoimeFriendBar(
    friend: Friend,
    modifier: Modifier = Modifier,
    trailingIconRes: ImageResource? = null,
    onAction: ((Friend) -> Unit)? = null
) {
    Row(
        modifier = modifier.then(Modifier.fillMaxWidth().padding(start = 7.5.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        MoimeProfileImage(
            imageUrl = friend.profileImageUrl,
            size = 40.dp,
            enableBorder = false,
            placeholderColor = Gray400
        )
        Spacer(Modifier.width(9.dp))
        Text(
            text = friend.nickname,
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_medium),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        trailingIconRes?.let { iconRes ->
            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                IconButton(onClick = { onAction?.let { it(friend) } }) {
                    Icon(
                        painterResource(iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Gray400
                    )
                }
            }
        }
    }
}
