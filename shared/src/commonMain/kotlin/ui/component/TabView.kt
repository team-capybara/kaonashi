package ui.component

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource

interface TabView {
    val titleRes: StringResource

    @Composable
    fun getTitleString(): String
}
