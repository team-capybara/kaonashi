import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.IntSize
import cafe.adriel.voyager.navigator.Navigator
import ui.theme.MoimeTheme


@Composable
fun App() {
    val screenSize = remember { mutableStateOf(IntSize(-1, -1)) }

    Layout(
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                MoimeTheme {
                    Navigator(ui.main.MainScreen(screenSize.value))
                }
            }
        },
        measurePolicy = { measurables, constraints ->
            val width = constraints.maxWidth
            val height = constraints.maxHeight

            screenSize.value = IntSize(width, height)
            
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
