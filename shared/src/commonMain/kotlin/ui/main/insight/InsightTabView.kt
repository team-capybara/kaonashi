package ui.main.insight

import androidx.compose.runtime.Composable
import moime.shared.generated.resources.Res
import moime.shared.generated.resources.insight_tab_friend
import moime.shared.generated.resources.insight_tab_summary
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ui.main.MainTabView

enum class InsightTabView(
    override val titleRes: StringResource
) : MainTabView {
    Summary(
        titleRes = Res.string.insight_tab_summary
    ),
    Friend(
        titleRes = Res.string.insight_tab_friend
    );

    @Composable
    override fun getTitleString(): String = stringResource(titleRes)
}
