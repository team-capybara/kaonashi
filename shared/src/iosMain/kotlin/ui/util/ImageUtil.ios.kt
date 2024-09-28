package ui.util

import androidx.compose.ui.graphics.ImageBitmap
import com.preat.peekaboo.image.picker.toImageBitmap
import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import kotlinx.cinterop.useContents
import platform.CoreGraphics.CGBitmapContextCreate
import platform.CoreGraphics.CGBitmapContextCreateImage
import platform.CoreGraphics.CGColorSpaceCreateDeviceRGB
import platform.CoreGraphics.CGImageAlphaInfo
import platform.CoreGraphics.CGRectMake
import platform.CoreGraphics.CGSize
import platform.CoreGraphics.CGSizeMake
import platform.UIKit.UIGraphicsBeginImageContextWithOptions
import platform.UIKit.UIGraphicsEndImageContext
import platform.UIKit.UIGraphicsGetImageFromCurrentImageContext
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation
import platform.posix.memcpy

actual fun ByteArray.resize(
    resizeOptions: ResizeOptions
): ByteArray {
    return with(resizeOptions) {
        toImageBitmap()
            .toUIImage()
            ?.fitInto(maxWidth, maxHeight, thresholdBytes)
            ?.toByteArray()
            ?: error("Failed to resize ImageBitmap of UIImage")
    }
}

@OptIn(ExperimentalForeignApi::class)
fun ImageBitmap.toUIImage(): UIImage? {
    val width = this.width
    val height = this.height
    val buffer = IntArray(width * height)

    this.readPixels(buffer)

    val colorSpace = CGColorSpaceCreateDeviceRGB()
    val context = CGBitmapContextCreate(
        data = buffer.refTo(0),
        width = width.toULong(),
        height = height.toULong(),
        bitsPerComponent = 8u,
        bytesPerRow = (4 * width).toULong(),
        space = colorSpace,
        bitmapInfo = CGImageAlphaInfo.kCGImageAlphaPremultipliedLast.value
    )

    val cgImage = CGBitmapContextCreateImage(context)
    return cgImage?.let { UIImage.imageWithCGImage(it) }
}

@OptIn(ExperimentalForeignApi::class)
private fun UIImage.toByteArray(): ByteArray {
    val pngData = UIImagePNGRepresentation(this)!!
    return ByteArray(pngData.length.toInt()).apply {
        memcpy(this.refTo(0), pngData.bytes, pngData.length)
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun UIImage.fitInto(
    maxWidth: Int,
    maxHeight: Int,
    resizeThresholdBytes: Long
): UIImage {
    val imageData = this.toByteArray()
    return if (imageData.size > resizeThresholdBytes) {
        val originalWidth = this.size.useContents { width }
        val originalHeight = this.size.useContents { height }
        val originalRatio = originalWidth / originalHeight

        val targetRatio = maxWidth.toDouble() / maxHeight.toDouble()
        val scale = if (originalRatio > targetRatio) {
            maxWidth.toDouble() / originalWidth
        } else {
            maxHeight.toDouble() / originalHeight
        }

        val newWidth = originalWidth * scale
        val newHeight = originalHeight * scale

        val targetSize = CGSizeMake(newWidth, newHeight)
        this.resize(targetSize)
    } else {
        this
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun UIImage.resize(targetSize: CValue<CGSize>): UIImage {
    UIGraphicsBeginImageContextWithOptions(targetSize, false, 0.0)
    this.drawInRect(
        CGRectMake(
            0.0,
            0.0,
            targetSize.useContents { width },
            targetSize.useContents { height },
        ),
    )
    val newImage = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()

    return newImage!!
}
