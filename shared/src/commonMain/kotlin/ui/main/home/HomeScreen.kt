package ui.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.koinScreenModel
import ui.main.MainScreenModel
import ui.theme.MoimeGreen

class HomeScreen : Screen {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val mainScreenModel = koinScreenModel<MainScreenModel>()
        val homeScreenModel = koinScreenModel<HomeScreenModel>()
        val homeState by homeScreenModel.state.collectAsState()

        when (mainScreenModel.tabViewState.currentHomeTabView) {
            HomeTabView.ListView ->
                if (homeState.homeListState.isMeetingInitialized) {
                    HomeListView(
                        state = homeState.homeListState,
                        onRefresh = { homeScreenModel.refreshListState() },
                        onLoadCompletedMeetings = { homeScreenModel.loadCompleteMeetings() },
                        isTodayMeetingVisible = mainScreenModel.topAppBarBackgroundVisible.not(),
                        onTodayMeetingVisibleChanged = {
                            mainScreenModel.setTopAppBarBackgroundVisibility(it.not())
                        }
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MoimeGreen)
                    }
                }

            HomeTabView.CalendarView -> {
                HomeCalendarView(
                    state = homeState.homeCalendarState,
                    onDayClicked = { day ->
                        homeScreenModel.loadMeetingOfDay(day) {
                            mainScreenModel.showMeetingsBottomSheet(it)
                        }
                    },
                    onRefresh = { homeScreenModel.refreshCalendarState() }
                )
            }
        }
    }
}
