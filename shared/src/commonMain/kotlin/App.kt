import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import ui.theme.MoimeTheme


@Composable
fun App() {
    MoimeTheme {
        Navigator(ui.main.MainScreen())
    }
}
