package ui.meeting

import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.preat.peekaboo.image.picker.toImageBitmap
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CameraScreenModel : StateScreenModel<CameraScreenModel.State>(State.Ready) {

    sealed interface State {
        data object Ready : State
        data class Captured(
            val photo: ImageBitmap,
            val isUploading: Boolean = false,
            val failedToUpload: Boolean = false
        ) : State
    }

    fun onCaptured(photo: ByteArray?) {
        screenModelScope.launch {
            photo?.let {
                mutableState.value = State.Captured(photo = it.toImageBitmap())
            } ?: run {
                showToast()
            }
        }
    }

    fun uploadPhoto() {
        if(state.value is State.Captured) {
            screenModelScope.launch {
                mutableState.value = (state.value as State.Captured).copy(isUploading = true)
                //TODO: upload photo to server
                delay(1000L)
                mutableState.value = State.Ready
            }
        }
    }

    fun clear() {
        screenModelScope.launch {
            mutableState.value = State.Ready
        }
    }

    fun showToast() {
        //TODO: show toast
    }
}
