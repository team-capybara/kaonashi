package ui.main.home

import dev.icerock.moko.resources.StringResource
import team.capybara.moime.SharedRes
import ui.main.MainTabView

enum class HomeTabView(
    override val titleRes: StringResource
): MainTabView {
    ListView(
        titleRes = SharedRes.strings.home_tab_list_view
    ),
    CalendarView(
        titleRes = SharedRes.strings.home_tab_calendar_view
    )
}
