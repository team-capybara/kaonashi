import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.theme.MoimeTheme


@Composable
@Preview
fun App() {
    MoimeTheme {
        ui.main.MainScreen()
    }
}
