package ui.friend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
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
import ui.component.MoimeTextField
import ui.theme.Gray400

@Composable
fun FriendSearchContent(
    myCode: String,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
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
            hintTextRes = SharedRes.strings.input_friend_code
        )
    }
}
