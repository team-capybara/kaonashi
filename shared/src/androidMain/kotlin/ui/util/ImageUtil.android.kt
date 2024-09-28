package ui.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

actual fun ByteArray.resize(
    resizeOptions: ResizeOptions
): ByteArray {
    val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }

    BitmapFactory.decodeByteArray(this, 0, size, options)

    var inSampleSize = 1
    while (options.outWidth / inSampleSize > resizeOptions.maxWidth ||
        options.outHeight / inSampleSize > resizeOptions.maxHeight
    ) {
        inSampleSize *= 2
    }

    options.inJustDecodeBounds = false
    options.inSampleSize = inSampleSize

    val resizedBitmap = BitmapFactory.decodeByteArray(this, 0, size, options)

    ByteArrayOutputStream().use { byteArrayOutputStream ->
        resizedBitmap.compress(
            Bitmap.CompressFormat.PNG,
            1,
            byteArrayOutputStream
        )
        return byteArrayOutputStream.toByteArray()
    }
}
