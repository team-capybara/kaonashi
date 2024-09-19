package ui.friend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch
import team.capybara.moime.SharedRes
import ui.component.MoimeDialog
import ui.component.MoimeFriendBar
import ui.component.MoimeIconButton
import ui.component.PaginationColumn
import ui.theme.Gray200
import ui.theme.Gray50
import ui.theme.Gray700
import ui.util.ShareUtil

data class FriendScreen(
    val myCode: String,
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val density = LocalDensity.current
        val coroutineScope = rememberCoroutineScope()
        val friendScreenModel = rememberScreenModel { FriendScreenModel() }
        val friendState by friendScreenModel.state.collectAsState()

        var selectedTabView by remember {
            mutableStateOf<FriendTabView>(FriendTabView.MyFriend(friendState.friendsCount))
        }

        Column(
            modifier =
                Modifier
                    .background(color = Gray700)
                    .padding(
                        top = with(density) { WindowInsets.statusBars.getTop(this).toDp() },
                        bottom = 20.dp,
                    ).fillMaxSize()
                    .padding(horizontal = 16.dp),
        ) {
            FriendTopAppBar(
                onClose = { navigator.pop() },
                onClickBlockList = { navigator.push(FriendBlockListScreen(friendScreenModel)) },
            )
            PaginationColumn(
                enablePaging = !friendState.isFriendListLoading,
                onPaging = {
                    when (selectedTabView) {
                        is FriendTabView.MyFriend -> friendScreenModel.loadMyFriends()
                        is FriendTabView.RecommendedFriend -> friendScreenModel.loadRecommendedFriends()
                    }
                },
                contentPadding = PaddingValues(bottom = 4.dp),
                modifier = Modifier.fillMaxWidth().weight(1f),
            ) {
                item {
                    FriendTitle()
                    Spacer(Modifier.height(36.dp))
                    FriendInvitation(
                        onShare = { ShareUtil.shareText(FriendScreenModel.SHARE_CONTENT_TEXT)}
                    )
                    Spacer(Modifier.height(30.dp))
                    FriendFindContent(
                        myCode = myCode,
                        foundUser = friendState.foundUser,
                        onSearch = { friendScreenModel.findUser(it) },
                        onAddFriend = { friendScreenModel.addFriend(it.id, it.nickname) },
                        onDismiss = { friendScreenModel.clearFoundUser() },
                    )
                    Spacer(Modifier.height(28.dp))
                    FriendListContentHeader(
                        tabViews =
                            listOf(
                                FriendTabView.MyFriend(friendState.friendsCount),
                                FriendTabView.RecommendedFriend(),
                            ),
                        selectedTabView = selectedTabView,
                        onTabViewChanged = { selectedTabView = it },
                        onSearch = {
                            coroutineScope.launch {
                                when (selectedTabView) {
                                    is FriendTabView.MyFriend ->
                                        friendScreenModel.searchMyFriends(it)

                                    is FriendTabView.RecommendedFriend ->
                                        friendScreenModel.searchRecommendedFriends(it)
                                }
                            }
                        },
                        onDismiss = {
                            when (selectedTabView) {
                                is FriendTabView.MyFriend -> friendScreenModel.clearSearchedMyFriends()
                                is FriendTabView.RecommendedFriend -> friendScreenModel.clearSearchedRecommendedFriends()
                            }
                        },
                    )
                    Spacer(Modifier.height(20.dp))
                }
                when (selectedTabView) {
                    is FriendTabView.MyFriend -> {
                        friendState.searchedMyFriends?.data?.let {
                            items(it) { friend ->
                                MoimeFriendBar(
                                    friend = friend,
                                    modifier = Modifier.padding(bottom = 16.dp),
                                )
                            }
                        } ?: items(friendState.myFriends.data) {
                            MoimeFriendBar(
                                friend = it,
                                modifier = Modifier.padding(bottom = 16.dp),
                            )
                        }
                    }

                    is FriendTabView.RecommendedFriend -> {
                        friendState.searchedRecommendedFriends?.data?.let {
                            items(it) { friend ->
                                MoimeFriendBar(
                                    friend = friend,
                                    action = {
                                        MoimeIconButton(SharedRes.images.ic_add) {
                                            friendScreenModel.addFriend(
                                                friend.id,
                                                friend.nickname,
                                            )
                                        }
                                    },
                                    modifier = Modifier.padding(bottom = 16.dp),
                                )
                            }
                        } ?: items(friendState.recommendedFriends.data) { friend ->
                            MoimeFriendBar(
                                friend = friend,
                                action = {
                                    MoimeIconButton(SharedRes.images.ic_add) {
                                        friendScreenModel.addFriend(
                                            friend.id,
                                            friend.nickname,
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
        friendState.dialogRequest?.let {
            MoimeDialog(
                request = it,
                onDismiss = { friendScreenModel.hideDialog() },
            )
        }
    }
}

@Composable
private fun FriendTopAppBar(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    onClickBlockList: () -> Unit,
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier =
            modifier.then(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        MoimeIconButton(SharedRes.images.ic_close, onClick = onClose)
        Box {
            MoimeIconButton(SharedRes.images.ic_more) { menuExpanded = true }
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                shape = RoundedCornerShape(8.dp),
                containerColor = Gray200,
            ) {
                DropdownMenuItem(
                    text = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = stringResource(SharedRes.strings.manage_blocked_friends),
                                fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
                                fontSize = 12.sp,
                                color = Gray700,
                            )
                        }
                    },
                    onClick = {
                        menuExpanded = false
                        onClickBlockList()
                    },
                    modifier = Modifier.height(24.dp).width(102.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                )
            }
        }
    }
}

@Composable
private fun FriendTitle(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(SharedRes.strings.add_friend),
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_bold),
            fontSize = 24.sp,
            color = Gray50,
        )
        Text(
            text = stringResource(SharedRes.strings.add_friend_desc),
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_medium),
            fontSize = 14.sp,
            color = Gray50,
        )
    }
}

@Composable
private fun FriendInvitation(
    modifier: Modifier = Modifier,
    onShare: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(SharedRes.strings.invite_app),
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_semibold),
            fontSize = 16.sp,
            color = Gray50,
        )
        Spacer(Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            InvitationType.entries.forEach {
                FriendInvitationButton(
                    imageRes = it.imageResource,
                    textRes = it.stringResource,
                    onClick = onShare
                )
            }
        }
    }
}

@Composable
private fun FriendInvitationButton(
    imageRes: ImageResource,
    textRes: StringResource,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.then(Modifier.width(56.dp).clickable { onClick() }),
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().aspectRatio(1f).clip(CircleShape),
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(textRes),
            fontFamily = fontFamilyResource(SharedRes.fonts.pretendard_medium),
            fontSize = 11.sp,
            color = Gray50,
        )
    }
}
