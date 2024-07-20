package ui.meeting.camera

sealed interface UploadState {
    data object Init : UploadState
    data object Uploading : UploadState
    data object Success : UploadState
    data object Failure : UploadState
}
