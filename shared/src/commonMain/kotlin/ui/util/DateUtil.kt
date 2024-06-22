package ui.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toInstant

object DateUtil {

    fun LocalDateTime.getDdayString(): String {
        val timeZone = TimeZone.currentSystemDefault()
        val diffInDays = toInstant(timeZone).daysUntil(Clock.System.now(), timeZone)
        return "D" + if (diffInDays > 0) {
            "+$diffInDays"
        } else if (diffInDays == 0) {
            "-day"
        } else {
            diffInDays.toString()
        }
    }

    fun LocalDateTime.getMonthDayString(): String = format("M월 d일")

    fun LocalDateTime.getTimeString(): String = format("HH:mm")

}

expect fun LocalDateTime.format(format: String): String