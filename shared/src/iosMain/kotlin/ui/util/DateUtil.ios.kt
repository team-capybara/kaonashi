package ui.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toNSDate
import platform.Foundation.NSCalendar
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDate

actual fun LocalDateTime.format(format: String): String {
    val dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = format
    return dateFormatter.stringFromDate(toInstant(TimeZone.currentSystemDefault()).toNSDate()
}
