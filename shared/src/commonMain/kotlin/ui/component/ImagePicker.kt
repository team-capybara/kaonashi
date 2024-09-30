package ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.preat.peekaboo.image.picker.ResizeOptions
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MoimeImagePicker(
    onPicked: (List<ByteArray>) -> Unit,
    scope: CoroutineScope = rememberCoroutineScope(),
    selectionMode: SelectionMode = SelectionMode.Single,
    resizeOptions: ResizeOptions = ResizeOptions(512, 512, 512L)
) {
    val imagePicker = rememberImagePickerLauncher(
        selectionMode = selectionMode,
        scope = scope,
        resizeOptions = resizeOptions,
        onResult = { onPicked(it) }
    )
    scope.launch { imagePicker.launch() }
}
