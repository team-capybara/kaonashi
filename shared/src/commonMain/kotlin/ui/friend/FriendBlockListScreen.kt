package ui.friend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.stringResource
import team.capybara.moime.SharedRes
import ui.component.MoimeFriendBar
import ui.component.MoimeSimpleTopAppBar
import ui.component.PaginationColumn
import ui.theme.Gray400
import ui.theme.Gray50
import ui.theme.Gray600
import ui.theme.Gray700
import ui.theme.MoimeRed

data class FriendBlockListScreen(
    private val friendScreenModel: FriendScreenModel
) : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val density = LocalDensity.current
        val friendState by friendScreenModel.state.collectAsState()

        Column(
            modifier = Modifier
                .background(color = Gray700)
                .padding(
                    top = with(density) { WindowInsets.statusBars.getTop(this).toDp() },
                    bottom = 20.dp,
                )
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            MoimeSimpleTopAppBar(
                backIconRes = SharedRes.images.ic_chevron_left,
                onBack = { navigator.pop() }
            )
            FriendBlockListHeader(count = friendState.blockedFriendsCount)
            Spacer(Modifier.height(12.dp))
            PaginationColumn(
                enablePaging = !friendState.isFriendListLoading,
                onPaging = { friendScreenModel.loadBlockedFriends() },
                contentPadding = PaddingValues(start = 8.dp, top = 24.dp, end = 8.dp, bottom = 8.dp)
            ) {
                items(friendState.blockedFriends.data) { friend ->
                    MoimeFriendBar(
                        friend = friend,
                        action = {
                            Button(
                                onClick = { friendScreenModel.unblockFriend(friend.id) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Gray600,
                                    contentColor = MoimeRed
                                ),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp)
                            ) {
                                Text(
                                    stringResource(SharedRes.strings.unblock_friend),
                                    fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
                                    fontSize = 12.sp,
                                )
                            }
                        },
                        modifier = Modifier.padding(bottom = 16.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun FriendBlockListHeader(
    count: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(SharedRes.strings.manage_blocked_friends),
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_bold),
            fontSize = 24.sp,
            color = Gray50
        )
        Spacer(Modifier.width(10.dp))
        Text(
            text = "${count}명",
            color = Gray400,
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_medium),
            fontSize = 14.sp
        )
    }
}
