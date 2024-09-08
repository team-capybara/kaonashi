package ui.main.insight

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import team.capybara.moime.SharedRes
import ui.main.TabView

enum class InsightTabView(
    override val titleRes: StringResource
) : TabView {
    Summary(
        titleRes = SharedRes.strings.insight_tab_summary
    ),
    Friend(
        titleRes = SharedRes.strings.insight_tab_friend
    );

    @Composable
    override fun getTitleString(): String = stringResource(titleRes)
}
