package ui.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs

object DateUtil {

    private val timeZone = TimeZone.currentSystemDefault()

    fun LocalDateTime.isEarlierThanNow(): Boolean {
        val now = LocalDateTime.now().toLocalDateTime(timeZone)
        return compareTo(now) > 0
    }

    fun LocalDateTime.getPeriodString(): String {
        val now = LocalDateTime.now()
        val period = this.toInstant(timeZone).periodUntil(now, timeZone)
        return "${period.hours.toFormattedPeriod()}:${period.minutes.toFormattedPeriod()}:${period.seconds.toFormattedPeriod()}"
    }

    fun LocalDateTime.getDdayString(): String {
        if (isToday()) return "D-day"
        val diffInDays = toInstant(timeZone).daysUntil(LocalDateTime.now(), timeZone)
        return "D" + if (diffInDays > 0) {
            "+$diffInDays"
        } else {
            diffInDays.toString()
        }
    }

    fun LocalDateTime.isToday(): Boolean {
        val today = LocalDateTime.now().toLocalDateTime(timeZone)
        return today.year == year &&
                today.monthNumber == monthNumber &&
                today.dayOfMonth == dayOfMonth
    }

    fun LocalDateTime.getMonthDayString(): String = format("M월 d일")

    fun LocalDateTime.getTimeString(): String = format("HH:mm")

    fun LocalDateTime.Companion.now() = Clock.System.now()

    private fun Int.toFormattedPeriod() =
        abs(this).toString().padStart(2, '0')
}

expect fun LocalDateTime.format(pattern: String): String