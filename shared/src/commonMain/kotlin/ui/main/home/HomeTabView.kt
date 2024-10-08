package ui.main.home

import androidx.compose.runtime.Composable
import moime.shared.generated.resources.Res
import moime.shared.generated.resources.home_tab_calendar_view
import moime.shared.generated.resources.home_tab_list_view
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import ui.main.MainTabView

enum class HomeTabView(
    override val titleTextRes: StringResource
) : MainTabView {
    ListView(
        titleTextRes = Res.string.home_tab_list_view
    ),
    CalendarView(
        titleTextRes = Res.string.home_tab_calendar_view
    );

    @Composable
    override fun getTitleText(): String = stringResource(titleTextRes)
}
