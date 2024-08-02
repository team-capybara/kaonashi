package ui.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import ui.component.MoimeMainTopAppBar
import ui.main.MainScreenModel
import ui.theme.MoimeGreen

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val mainScreenModel = koinScreenModel<MainScreenModel>()
        val homeScreenModel = rememberScreenModel { HomeScreenModel() }
        val mainState by mainScreenModel.state.collectAsState()
        val homeState by homeScreenModel.state.collectAsState()

        val user = mainScreenModel.getUser()
        var isTodayMeetingVisible by remember { mutableStateOf(false) }
        var currentTabView by remember { mutableStateOf(HomeTabView.ListView) }

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
                when (mainState.tabViewState.currentHomeTabView) {
                    HomeTabView.ListView ->
                        HomeListView(
                            meetings = (state as HomeScreenModel.State.Result).meetings,
                            homeState = state,
                            onRefresh = { homeScreenModel.refreshMeetings(true) },
                            isTodayMeetingVisible = isTodayMeetingVisible,
                            onTodayMeetingVisibleChanged = {
                                isTodayMeetingVisible = it
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
            }
        }
    }
}
