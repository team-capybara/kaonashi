package ui.main.home

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import team.capybara.moime.SharedRes
import ui.main.MainTabView

enum class HomeTabView(
    override val titleRes: StringResource
) : MainTabView {
    ListView(
        titleRes = SharedRes.strings.home_tab_list_view
    ),
    CalendarView(
        titleRes = SharedRes.strings.home_tab_calendar_view
    );

    @Composable
    override fun getTitleString(): String = stringResource(titleRes)
}
