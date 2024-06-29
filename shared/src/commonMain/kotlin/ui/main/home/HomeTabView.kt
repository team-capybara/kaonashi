package ui.main.home

import dev.icerock.moko.resources.StringResource
import team.capybara.moime.SharedRes

enum class HomeTabView(
    val textRes: StringResource
) {
    ListView(
        textRes = SharedRes.strings.home_tab_list_view
    ),
    CalendarView(
        textRes = SharedRes.strings.home_tab_calendar_view
    )
}
