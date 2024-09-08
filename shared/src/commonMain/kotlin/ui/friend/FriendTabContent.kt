package ui.friend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import team.capybara.moime.SharedRes
import ui.component.MoimeFriendBar
import ui.component.MoimeTextField
import ui.component.TabViewSegmentedButtonBar

@Composable
fun FriendTabContent(
    state: FriendScreenModel.State,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    val friendCount = state.friendCount
    var selectedTabView by remember {
        mutableStateOf<FriendTabView>(FriendTabView.MyFriend(friendCount = friendCount))
    }

    Column(
        modifier = modifier.then(Modifier.fillMaxWidth()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabViewSegmentedButtonBar(
            tabViews = listOf(
                FriendTabView.MyFriend(friendCount = friendCount),
                FriendTabView.RecommendedFriend()
            ),
            selected = selectedTabView,
            onClickFirstTabView = {
                selectedTabView = FriendTabView.MyFriend(friendCount = friendCount)
            },
            onClickSecondTabView = { selectedTabView = FriendTabView.RecommendedFriend() }
        )
        Spacer(Modifier.height(12.dp))
        MoimeTextField(
            modifier = Modifier.fillMaxWidth(),
            imeAction = ImeAction.Search,
            onSearch = onSearch,
            hintTextRes = SharedRes.strings.search_friend_via_nickname
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(top = 20.dp),
            contentPadding = PaddingValues(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (selectedTabView) {
                is FriendTabView.MyFriend -> {
                    items(state.myFriends) {
                        MoimeFriendBar(it)
                    }
                }

                is FriendTabView.RecommendedFriend -> {
                    items(state.recommendedFriends) {
                        MoimeFriendBar(
                            friend = it,
                            trailingIconRes = SharedRes.images.ic_add,
                            onAction = {}
                        )
                    }
                }
            }
        }
    }
}
