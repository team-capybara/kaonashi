package ui.meeting.camera

import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.ashampoo.kim.Kim
import com.ashampoo.kim.model.GpsCoordinates
import com.ashampoo.kim.model.MetadataUpdate
import com.preat.peekaboo.image.picker.toImageBitmap
import dev.icerock.moko.geo.LatLng
import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CameraScreenModel(
    private val locationTracker: LocationTracker
) : StateScreenModel<CameraScreenModel.CameraState>(CameraState()) {

    data class CameraState(
        val location: LatLng? = null,
        val photo: ImageBitmap? = null,
        val uploadState: CameraUploadState = CameraUploadState.Init,
        val toast: CameraToastType? = CameraToastType.LoadingLocation
    )

    init {
        locationTracker.getLocationsFlow()
            .distinctUntilChanged()
            .onEach {
                if (state.value.location == null) {
                    mutableState.value = state.value.copy(toast = null)
                }
                mutableState.value = state.value.copy(location = it)
            }
            .launchIn(screenModelScope)
    }

    fun startLocationTracker() {
        screenModelScope.launch {
            locationTracker.startTracking()
        }
    }

    fun stopLocationTracker() {
        locationTracker.stopTracking()
    }

    fun onCaptured(photo: ByteArray?) {
        screenModelScope.launch {
            state.value.location?.let { location ->
                photo?.let {
                    Kim.update(
                        bytes = photo,
                        update = MetadataUpdate.GpsCoordinates(
                            gpsCoordinates = GpsCoordinates(
                                latitude = location.latitude,
                                longitude = location.longitude
                            )
                        ),
                    )
                    mutableState.value = state.value.copy(photo = it.toImageBitmap())
                } ?: run {
                    mutableState.value = state.value.copy(toast = CameraToastType.CaptureFailure)
                }
            } ?: run {
                mutableState.value = state.value.copy(toast = CameraToastType.LocationFailure)
            }
        }
    }

    fun uploadPhoto() {
        state.value.photo?.let { photo ->
            screenModelScope.launch {
                mutableState.value = state.value.copy(
                    uploadState = CameraUploadState.Uploading,
                    toast = CameraToastType.Uploading
                )

                delay(1000L) //TODO: upload photo to server

                mutableState.value = state.value.copy(
                    uploadState = CameraUploadState.Success,
                    toast = CameraToastType.UploadSuccess
                )

                delay(800L)

                mutableState.value = state.value.copy(
                    photo = null,
                    uploadState = CameraUploadState.Init,
                    toast = null
                )
            }
        }
    }

    fun clear() {
        mutableState.value = state.value.copy(
            photo = null,
            uploadState = CameraUploadState.Init,
            toast = null
        )
    }
}
