package ui.meeting.camera

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.preat.peekaboo.ui.camera.PeekabooCamera
import com.preat.peekaboo.ui.camera.rememberPeekabooCameraState
import dev.icerock.moko.resources.compose.painterResource
import team.capybara.moime.SharedRes
import ui.theme.Gray100
import ui.theme.Gray400
import ui.theme.Gray50
import ui.theme.Gray500
import ui.theme.Gray700
import ui.theme.Gray800
import ui.theme.MoimeGreen

class CameraScreen : Screen {

    @Composable
    override fun Content() {
        val cameraScreenModel = rememberScreenModel { CameraScreenModel() }
        val uiState by cameraScreenModel.state.collectAsState()
        var toastState by remember { mutableStateOf(CameraToastState()) }
        val cameraState = rememberPeekabooCameraState(
            onCapture = cameraScreenModel::onCaptured
        )

        LaunchedEffect(uiState) {
            toastState = CameraToastState.create(uiState)
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        painterResource(SharedRes.images.ic_close),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Spacer(Modifier.weight(76f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                if (uiState is CameraUiState.Captured) {
                    Image(
                        (uiState as CameraUiState.Captured).photo,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp))
                    )
                } else {
                    PeekabooCamera(
                        state = cameraState,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp)),
                        permissionDeniedContent = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .background(color = Color.Black)
                                    .clip(RoundedCornerShape(20.dp))
                            )
                        }
                    )
                }
                if (uiState is CameraUiState.Captured &&
                    (uiState as CameraUiState.Captured).uploadState != UploadState.Uploading &&
                    (uiState as CameraUiState.Captured).uploadState != UploadState.Success
                ) {
                    FilledIconButton(
                        onClick = { cameraScreenModel.clear() },
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .size(36.dp)
                            .align(Alignment.BottomCenter),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = Gray500,
                            contentColor = Gray50
                        )
                    ) {
                        Icon(
                            painterResource(SharedRes.images.ic_delete),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(284f)
                    .fillMaxWidth()
                    .padding(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (uiState !is CameraUiState.Captured) {
                        IconButton(
                            onClick = {},
                            enabled = with(cameraState) { isCameraReady && isCapturing.not() }
                        ) {
                            Icon(
                                painterResource(SharedRes.images.ic_moment),
                                contentDescription = null,
                                modifier = Modifier.size(36.dp),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    if (uiState is CameraUiState.Captured) {
                        val uploadState = (uiState as CameraUiState.Captured).uploadState
                        FilledIconButton(
                            onClick = {
                                if (uploadState != UploadState.Uploading) {
                                    cameraScreenModel.uploadPhoto()
                                }
                            },
                            modifier = Modifier.size(80.dp),
                            shape = CircleShape,
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MoimeGreen,
                                contentColor = Gray700,
                                disabledContainerColor = Gray800,
                                disabledContentColor = Gray400
                            ),
                            enabled = uploadState != UploadState.Success
                        ) {
                            if (uploadState == UploadState.Uploading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(28.dp),
                                    color = Gray700,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                when (uploadState) {
                                    UploadState.Init -> SharedRes.images.ic_export_2
                                    UploadState.Success -> SharedRes.images.ic_done
                                    UploadState.Failure -> SharedRes.images.ic_reload
                                    UploadState.Uploading -> null
                                }?.let {
                                    Icon(
                                        painterResource(it),
                                        contentDescription = null,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .border(width = 4.dp, color = MoimeGreen, shape = CircleShape)
                                .size(80.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            FilledTonalButton(
                                onClick = { cameraState.capture() },
                                modifier = Modifier.size(64.dp),
                                shape = CircleShape,
                                colors = ButtonDefaults.filledTonalButtonColors(
                                    containerColor = Gray50,
                                    disabledContainerColor = Gray100
                                ),
                                enabled = with(cameraState) { isCameraReady && isCapturing.not() }
                            ) {}
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    if (uiState !is CameraUiState.Captured) {
                        IconButton(
                            onClick = { cameraState.toggleCamera() },
                            enabled = with(cameraState) { isCameraReady && isCapturing.not() }
                        ) {
                            Icon(
                                painterResource(SharedRes.images.ic_refresh),
                                contentDescription = null,
                                modifier = Modifier.size(36.dp),
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
                Spacer(Modifier.height(40.dp))
                if (toastState.visible) {
                    CameraToast(toastState)
                }
            }
        }
    }
}