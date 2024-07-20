package ui.meeting.camera

import dev.icerock.moko.resources.StringResource
import team.capybara.moime.SharedRes

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
                        message = SharedRes.strings.failed_to_capture,
                        type = Type.Error
                    )
                }

                is CameraUiState.Captured -> {
                    when (cameraUiState.uploadState) {
                        UploadState.Uploading -> {
                            CameraToastState(
                                visible = true,
                                message = SharedRes.strings.uploading_photo,
                                type = Type.Normal
                            )
                        }

                        UploadState.Success -> {
                            CameraToastState(
                                visible = true,
                                message = SharedRes.strings.upload_done,
                                type = Type.Normal
                            )
                        }

                        UploadState.Failure -> {
                            CameraToastState(
                                visible = true,
                                message = SharedRes.strings.upload_failure,
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
