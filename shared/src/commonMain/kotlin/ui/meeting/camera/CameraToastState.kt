package ui.meeting.camera

import moime.shared.generated.resources.Res
import moime.shared.generated.resources.failed_to_capture
import moime.shared.generated.resources.upload_done
import moime.shared.generated.resources.upload_failure
import moime.shared.generated.resources.uploading_photo
import org.jetbrains.compose.resources.StringResource

data class CameraToastState(
    val visible: Boolean = false,
    val message: StringResource? = null,
    val type: Type = Type.Normal
) {
    enum class Type {
        Normal, Error
    }

    companion object {
        fun create(cameraUiState: CameraUiState) =
            when (cameraUiState) {
                CameraUiState.Failure -> {
                    CameraToastState(
                        visible = true,
                        message = Res.string.failed_to_capture,
                        type = Type.Error
                    )
                }

                is CameraUiState.Captured -> {
                    when (cameraUiState.uploadState) {
                        UploadState.Uploading -> {
                            CameraToastState(
                                visible = true,
                                message = Res.string.uploading_photo,
                                type = Type.Normal
                            )
                        }

                        UploadState.Success -> {
                            CameraToastState(
                                visible = true,
                                message = Res.string.upload_done,
                                type = Type.Normal
                            )
                        }

                        UploadState.Failure -> {
                            CameraToastState(
                                visible = true,
                                message = Res.string.upload_failure,
                                type = Type.Error
                            )
                        }

                        else -> {
                            CameraToastState()
                        }
                    }
                }

                else -> {
                    CameraToastState()
                }
            }
    }
}
