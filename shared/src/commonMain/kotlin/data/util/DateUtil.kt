package data.util

object DateUtil {
    fun String.toISO(): String {
        val year = substring(0, 4)
        val month = substring(4, 6)
        val day = substring(6, 8)
        val hour = substring(8, 10)
        val minute = substring(10, 12)
        val second = substring(12, 14)

        return "$year-$month-${day}T$hour:$minute:$second"
    }
}
