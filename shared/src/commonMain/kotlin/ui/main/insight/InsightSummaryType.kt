package ui.main.insight

import moime.shared.generated.resources.Res
import moime.shared.generated.resources.insight_title_friend
import moime.shared.generated.resources.insight_title_meeting
import moime.shared.generated.resources.insight_title_time
import org.jetbrains.compose.resources.StringResource

enum class InsightSummaryType(
    val titleRes: StringResource
) {
    Friend(Res.string.insight_title_friend),
    Meeting(Res.string.insight_title_meeting),
    Time(Res.string.insight_title_time)
}
