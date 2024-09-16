package ui.friend

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.stringResource
import team.capybara.moime.SharedRes
import ui.component.MoimeProfileImage
import ui.component.MoimeTextField
import ui.model.Stranger
import ui.theme.Gray200
import ui.theme.Gray400
import ui.theme.Gray50
import ui.theme.Gray500
import ui.theme.Gray700
import ui.util.DateUtil.daysUntilNow

@Composable
fun FriendFindContent(
    myCode: String,
    foundUser: Stranger?,
    onSearch: (String) -> Unit,
    onAddFriend: (Stranger) -> Unit,
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
                stringResource(SharedRes.strings.search_friend_via_code),
                fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "${stringResource(SharedRes.strings.my_friend_code)} $myCode",
                fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_medium),
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
            hintTextRes = SharedRes.strings.input_friend_code
        )
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
    foundUser: Stranger?,
    onAddFriend: (Stranger) -> Unit,
    myCode: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.then(Modifier.padding(top = 28.dp)),
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
                fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
                fontSize = 12.sp,
                color = Gray50
            )
            if (foundUser?.friendshipDateTime == null) {
                if (foundUser?.code == myCode) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        stringResource(SharedRes.strings.it_is_me),
                        fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
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
                            stringResource(SharedRes.strings.add_friend),
                            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
                            fontSize = 12.sp,
                        )
                    }
                }
            } else {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "친구가 된지 ${foundUser.friendshipDateTime.daysUntilNow()}일째",
                    fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
                    fontSize = 12.sp,
                    color = Gray400
                )
            }
        }
    }
}
