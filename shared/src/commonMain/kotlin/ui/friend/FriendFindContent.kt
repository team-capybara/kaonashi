package ui.friend

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import moime.shared.generated.resources.Res
import moime.shared.generated.resources.add_friend
import moime.shared.generated.resources.input_friend_code
import moime.shared.generated.resources.it_is_me
import moime.shared.generated.resources.my_friend_code
import moime.shared.generated.resources.search_friend_via_code
import org.jetbrains.compose.resources.stringResource
import ui.component.MoimeProfileImage
import ui.component.MoimeTextField
import ui.model.Friend
import ui.theme.Gray200
import ui.theme.Gray400
import ui.theme.Gray50
import ui.theme.Gray500
import ui.theme.Gray700
import ui.util.DateUtil.daysUntilNow

@Composable
fun FriendFindContent(
    myCode: String,
    foundUser: Friend?,
    onSearch: (String) -> Unit,
    onAddFriend: (Friend) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.then(Modifier.fillMaxWidth())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(Res.string.search_friend_via_code),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(Res.string.my_friend_code, myCode),
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Gray400
            )
        }
        Spacer(Modifier.height(18.dp))
        MoimeTextField(
            modifier = Modifier.fillMaxWidth(),
            imeAction = ImeAction.Done,
            onDone = onSearch,
            onDismiss = onDismiss,
            hintTextRes = Res.string.input_friend_code
        )
        Spacer(Modifier.height(28.dp))
        AnimatedVisibility(
            visible = foundUser != null
        ) {
            FriendFindCard(
                foundUser = foundUser,
                onAddFriend = onAddFriend,
                myCode = myCode
            )
        }
    }
}

@Composable
private fun FriendFindCard(
    foundUser: Friend?,
    onAddFriend: (Friend) -> Unit,
    myCode: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = Gray500,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.height(206.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MoimeProfileImage(
                imageUrl = foundUser?.profileImageUrl ?: "",
                size = 80.dp,
                enableBorder = false
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = foundUser?.nickname ?: "",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Gray50
            )
            if (foundUser?.friendshipDateTime == null) {
                if (foundUser?.code == myCode) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        stringResource(Res.string.it_is_me),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Gray400
                    )
                } else {
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = { foundUser?.let { onAddFriend(it) } },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Gray200,
                            contentColor = Gray700
                        ),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            stringResource(Res.string.add_friend),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                        )
                    }
                }
            } else {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "친구가 된지 ${foundUser.friendshipDateTime.daysUntilNow()}일째",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Gray400
                )
            }
        }
    }
}
