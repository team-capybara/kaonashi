package ui.main.insight

import dev.icerock.moko.resources.StringResource
import team.capybara.moime.SharedRes
import ui.main.MainTabView

enum class InsightTabView(
    override val titleRes: StringResource
): MainTabView {
    Summary(
        titleRes = SharedRes.strings.insight_tab_summary
    ),
    Friend(
        titleRes = SharedRes.strings.insight_tab_friend
    )
}
