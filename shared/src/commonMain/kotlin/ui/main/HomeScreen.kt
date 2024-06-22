package ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.component.MoimeMainTopAppBar
import ui.main.tab.home.HomeTabView
import ui.theme.Gray50

class HomeScreen : Screen, KoinComponent {

    private val hazeState: HazeState by inject()

    @Composable
    override fun Content() {
        var currentTabView: HomeTabView by remember { mutableStateOf(HomeTabView.ListView) }

        Scaffold(
            topBar = {
                MoimeMainTopAppBar(
                    hazeState = hazeState,
                    profileImageUrl = "https://play-lh.googleusercontent.com/Kbu0747Cx3rpzHcSbtM1zDriGFG74zVbtkPmVnOKpmLCS59l7IuKD5M3MKbaq_nEaZM",
                    selectedTabView = currentTabView,
                    onClickUserAdd = {},
                    onClickNotification = {},
                    onClickListView = { currentTabView = HomeTabView.ListView },
                    onClickCalendarView = { currentTabView = HomeTabView.CalendarView }
                )
            },
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().haze(state = hazeState)
                    .windowInsetsPadding(WindowInsets.safeDrawing),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(100) {
                    Card(
                        modifier = Modifier.fillMaxWidth().height(128.dp),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize().padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "HomeScreen ".repeat(6),
                                color = Gray50,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}