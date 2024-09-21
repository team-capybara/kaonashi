package ui.friend

import androidx.compose.runtime.Composable
import moime.shared.generated.resources.Res
import moime.shared.generated.resources.my_friend
import moime.shared.generated.resources.recommended_friend
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ui.component.TabView

sealed class FriendTabView(
    override val titleRes: StringResource
) : TabView {
    data class MyFriend(
        val friendCount: Int,
        override val titleRes: StringResource = Res.string.my_friend
    ) : FriendTabView(titleRes) {

        @Composable
        override fun getTitleString() = "${stringResource(titleRes)} $friendCount"
    }

    data class RecommendedFriend(
        override val titleRes: StringResource = Res.string.recommended_friend
    ) : FriendTabView(titleRes) {

        @Composable
        override fun getTitleString(): String = stringResource(titleRes)
    }
}
