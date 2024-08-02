package ui

import androidx.compose.runtime.staticCompositionLocalOf
import dev.chrisbanes.haze.HazeState

val LocalHazeState = staticCompositionLocalOf { HazeState() }
