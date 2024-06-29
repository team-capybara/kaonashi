package ui.main.home

import dev.icerock.moko.resources.StringResource
import team.capybara.moime.SharedRes

enum class HomeTabView(
    val textRes: StringResource,
    val text: String
) {
    ListView(
        textRes = SharedRes.strings.home_tab_list_view,
        text = "리스트 뷰"
    ),
    CalendarView(
        textRes = SharedRes.strings.home_tab_calendar_view,
        text = "캘린더 뷰"
    )
}
