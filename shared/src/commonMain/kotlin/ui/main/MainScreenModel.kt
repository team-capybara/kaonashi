package ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.model.ScreenModel
import dev.chrisbanes.haze.HazeState

class MainScreenModel() : ScreenModel {

    val hazeState: HazeState
        @Composable
        get() = remember { HazeState() }
}
