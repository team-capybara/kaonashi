package ui.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import ui.main.MainScreenModel
import ui.theme.MoimeGreen

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val mainScreenModel = koinScreenModel<MainScreenModel>()
        val homeScreenModel = rememberScreenModel { HomeScreenModel() }
        val homeState by homeScreenModel.state.collectAsState()

        when (homeState) {
            is HomeScreenModel.State.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MoimeGreen)
                }
            }

            is HomeScreenModel.State.Result -> {
                val state = homeState as HomeScreenModel.State.Result
                if (state.meetings.isNotEmpty()) {
                    when (mainScreenModel.tabViewState.currentHomeTabView) {
                        HomeTabView.ListView ->
                            HomeListView(
                                meetings = state.meetings,
                                homeState = state,
                                onRefresh = { homeScreenModel.refreshMeetings(true) },
                                isTodayMeetingVisible = mainScreenModel.topAppBarBackgroundVisible.not(),
                                onTodayMeetingVisibleChanged = {
                                    mainScreenModel.setTopAppBarBackgroundVisibility(it.not())
                                }
                            )

                        HomeTabView.CalendarView -> {
                            HomeCalendarView(
                                meetings = (homeState as HomeScreenModel.State.Result).meetings,
                                onDayClicked = { currentDayMeetings ->
                                    mainScreenModel.showMeetingsBottomSheet(currentDayMeetings)
                                }
                            )
                        }
                    }
                } else {
                    // empty meetings
                }
            }
        }
    }
}
