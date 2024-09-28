package ui.util

/**
 * Compress with JPEG format and sampling the image with given options.
 */
expect fun ByteArray.resize(
    resizeOptions: ResizeOptions
): ByteArray

data class ResizeOptions(
    val maxWidth: Int = 512,
    val maxHeight: Int = 512,
    val thresholdBytes: Long = 524288L
)
