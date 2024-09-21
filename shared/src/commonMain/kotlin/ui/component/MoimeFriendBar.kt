package ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ui.model.Friend
import ui.theme.Gray400

@Composable
fun MoimeFriendBar(
    friend: Friend,
    modifier: Modifier = Modifier,
    action: @Composable (RowScope.() -> Unit)? = null
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
            fontWeight = FontWeight.Medium,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        action?.let { it() }
    }
}
