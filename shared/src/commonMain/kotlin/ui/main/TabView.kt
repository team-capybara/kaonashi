package ui.main

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource

interface TabView {
    val titleRes: StringResource

    @Composable
    fun getTitleString(): String
}
