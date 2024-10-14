package ui.friend

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

data class FriendDetailScreen(private val targetId: Long) : Screen, KoinComponent {

    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel { FriendDetailScreenModel(targetId, get(), get()) }
    }
}
