package ui.meeting.camera

import androidx.compose.ui.graphics.ImageBitmap

sealed interface CameraUiState {
    data object Ready : CameraUiState
    data class Captured(
        val photo: ImageBitmap,
        val uploadState: UploadState = UploadState.Init
    ) : CameraUiState

    data object Failure : CameraUiState
}
