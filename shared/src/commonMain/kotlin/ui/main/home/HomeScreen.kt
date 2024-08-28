package ui.main.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import ui.component.MoimeHomeTopAppBar
import ui.component.MoimeLoading
import ui.main.MainScreenModel

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val mainScreenModel = koinScreenModel<MainScreenModel>()
        val homeScreenModel = rememberScreenModel { HomeScreenModel() }
        val state by homeScreenModel.state.collectAsState()

        var isTodayMeetingVisible by remember { mutableStateOf(false) }
        var currentTabView: HomeTabView by remember { mutableStateOf(HomeTabView.ListView) }

        Scaffold(
            topBar = {
                MoimeHomeTopAppBar(
                    profileImageUrl = "https://play-lh.googleusercontent.com/Kbu0747Cx3rpzHcSbtM1zDriGFG74zVbtkPmVnOKpmLCS59l7IuKD5M3MKbaq_nEaZM",
                    selectedTabView = currentTabView,
                    onClickUserAdd = {},
                    onClickNotification = {},
                    onClickListView = { currentTabView = HomeTabView.ListView },
                    onClickCalendarView = { currentTabView = HomeTabView.CalendarView },
                    isTodayMeetingVisible = isTodayMeetingVisible
                )
            },
        ) { innerPadding ->
            when (state) {
                is HomeScreenModel.State.Loading -> {
                    MoimeLoading(modifier = Modifier.padding(innerPadding))
                }

                is HomeScreenModel.State.Result -> {
                    when (currentTabView) {
                        HomeTabView.ListView ->
                            HomeListView(
                                meetings = (state as HomeScreenModel.State.Result).meetings,
                                isTodayMeetingVisible = isTodayMeetingVisible,
                                onTodayMeetingVisibleChanged = {
                                    isTodayMeetingVisible = it
                                }
                            )

                        HomeTabView.CalendarView -> {
                            HomeCalendarView(
                                meetings = (state as HomeScreenModel.State.Result).meetings,
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
}
