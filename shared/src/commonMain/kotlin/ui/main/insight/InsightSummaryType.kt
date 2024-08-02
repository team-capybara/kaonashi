package ui.main.insight

import dev.icerock.moko.resources.StringResource
import team.capybara.moime.SharedRes

enum class InsightSummaryType(
    val titleRes: StringResource
) {
    Friend(SharedRes.strings.insight_title_friend),
    Meeting(SharedRes.strings.insight_title_meeting),
    Time(SharedRes.strings.insight_title_time)
}
