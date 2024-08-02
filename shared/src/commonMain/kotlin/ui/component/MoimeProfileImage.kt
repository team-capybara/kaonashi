package ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import ui.theme.Gray200
import ui.theme.Gray500

@Composable
fun MoimeProfileImage(
    imageUrl: String,
    size: Dp? = null,
    enableBorder: Boolean = true,
    placeholderColor: Color = Gray200,
    modifier: Modifier = Modifier
) {
    CoilImage(
        imageModel = { imageUrl },
        imageOptions = ImageOptions(
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        ),
        loading = {
            MoimeImagePlaceholder(
                size = size,
                color = placeholderColor,
                shape = CircleShape
            )
        },
        failure = {
            MoimeImagePlaceholder(
                size = size,
                color = placeholderColor,
                shape = CircleShape
            )
        },
        modifier = modifier
            .then(if (enableBorder) Modifier.border(1.dp, Gray500, CircleShape) else Modifier)
            .then(size?.let { Modifier.size(it) } ?: Modifier)
            .then(Modifier.clip(CircleShape))
    )
}
