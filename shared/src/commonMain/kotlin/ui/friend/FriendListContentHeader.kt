package ui.friend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import team.capybara.moime.SharedRes
import ui.component.MoimeTextField
import ui.component.TabViewSegmentedButtonBar

@Composable
fun FriendListContentHeader(
    tabViews: List<FriendTabView>,
    selectedTabView: FriendTabView,
    onTabViewChanged: (FriendTabView) -> Unit,
    onSearch: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    Column(
        modifier = modifier.then(Modifier.fillMaxWidth()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabViewSegmentedButtonBar(
            tabViews = tabViews,
            selected = selectedTabView,
            onTabViewChanged = { onTabViewChanged(it) }
        )
        Spacer(Modifier.height(12.dp))
        MoimeTextField(
            modifier = Modifier.fillMaxWidth(),
            imeAction = ImeAction.Search,
            onSearch = onSearch,
            onDismiss = onDismiss,
            hintTextRes = SharedRes.strings.search_friend_via_nickname
        )
    }
}
