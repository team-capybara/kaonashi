package ui.meeting.camera

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.preat.peekaboo.image.picker.toImageBitmap
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CameraScreenModel : StateScreenModel<CameraUiState>(CameraUiState.Ready) {

    fun onCaptured(photo: ByteArray?) {
        screenModelScope.launch {
            photo?.let {
                mutableState.value = CameraUiState.Captured(photo = it.toImageBitmap())
            } ?: run {
                mutableState.value = CameraUiState.Failure
            }
        }
    }

    fun uploadPhoto() {
        if (state.value is CameraUiState.Captured) {
            screenModelScope.launch {
                mutableState.value = (state.value as CameraUiState.Captured)
                    .copy(uploadState = UploadState.Uploading)

                delay(1000L) //TODO: upload photo to server

                mutableState.value = (state.value as CameraUiState.Captured)
                    .copy(uploadState = UploadState.Success)
                delay(800L)
                mutableState.value = CameraUiState.Ready
            }
        }
    }

    fun clear() {
        screenModelScope.launch {
            mutableState.value = CameraUiState.Ready
        }
    }
}
