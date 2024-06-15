package ui.main.tab

import cafe.adriel.voyager.navigator.tab.Tab
import dev.icerock.moko.resources.ImageResource

sealed interface MainTab : Tab {
    val iconResource: ImageResource
}
