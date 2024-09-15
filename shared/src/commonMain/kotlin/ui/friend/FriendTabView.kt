package ui.friend

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import team.capybara.moime.SharedRes
import ui.component.TabView

sealed class FriendTabView(
    override val titleRes: StringResource
) : TabView {
    data class MyFriend(
        val friendCount: Int,
        override val titleRes: StringResource = SharedRes.strings.my_friend
    ) : FriendTabView(titleRes) {

        @Composable
        override fun getTitleString() = "${stringResource(titleRes)} $friendCount"
    }

    data class RecommendedFriend(
        override val titleRes: StringResource = SharedRes.strings.recommended_friend
    ) : FriendTabView(titleRes) {

        @Composable
        override fun getTitleString(): String = stringResource(titleRes)
    }
}
