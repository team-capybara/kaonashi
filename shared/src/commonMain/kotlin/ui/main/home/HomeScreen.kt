package ui.main.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.component.MoimeHomeTopAppBar
import ui.theme.MoimeGreen

class HomeScreen : Screen, KoinComponent {

    private val hazeState: HazeState by inject()

    @Composable
    override fun Content() {
        val homeScreenModel = rememberScreenModel { HomeScreenModel() }
        val state by homeScreenModel.state.collectAsState()
        var isTodayMeetingVisible by remember { mutableStateOf(false) }
        var currentTabView: HomeTabView by remember { mutableStateOf(HomeTabView.ListView) }
        val gradient = Brush.verticalGradient(listOf(Color(0xCC25FF89), Color(0x0000E8BE)))

        Scaffold(
            topBar = {
                MoimeHomeTopAppBar(
                    hazeState = hazeState,
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
                    Box(
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MoimeGreen)
                    }
                }

                is HomeScreenModel.State.Result -> {
                    when (currentTabView) {
                        HomeTabView.ListView -> {
                            AnimatedContent(isTodayMeetingVisible) { targetState ->
                                HomeListView(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .then(
                                            if (!targetState) {
                                                Modifier.background(gradient)
                                            } else {
                                                Modifier
                                            }
                                        )
                                        .haze(state = hazeState),
                                    meetings = (state as HomeScreenModel.State.Result).meetings,
                                    onTodayMeetingVisibleChanged = {
                                        isTodayMeetingVisible = it
                                    }
                                )
                            }
                        }

                        HomeTabView.CalendarView -> {}
                    }
                }
            }
        }
    }
}
