import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import dev.chrisbanes.haze.HazeState
import ui.LocalHazeState
import ui.LocalScreenSize
import ui.ScreenSize
import ui.theme.MoimeTheme


@OptIn(ExperimentalVoyagerApi::class)
@Composable
fun App() {
    var screenSize by remember { mutableStateOf(ScreenSize()) }

    Layout(
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                MoimeTheme {
                    CompositionLocalProvider(
                        LocalScreenSize provides screenSize,
                        LocalHazeState provides HazeState()
                    ) {
                        Navigator(
                            screen = ui.splash.SplashScreen(),
                            disposeBehavior = NavigatorDisposeBehavior(disposeSteps = false)
                        ) { navigator ->
                            SlideTransition(
                                navigator = navigator,
                                disposeScreenAfterTransitionEnd = true
                            )
                        }
                    }
                }
            }
        },
        measurePolicy = { measurables, constraints ->
            val width = constraints.maxWidth
            val height = constraints.maxHeight

            screenSize = ScreenSize(width, height)

            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            layout(width, height) {
                var yPosition = 0
                placeables.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = yPosition)
                    yPosition += placeable.height
                }
            }
        }
    )
}
