package ui.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs

object DateUtil {

    val timeZone = TimeZone.currentSystemDefault()

    fun DayOfWeek.toKr(): String {
        return when (this.isoDayNumber) {
            1 -> "월"
            2 -> "화"
            3 -> "수"
            4 -> "목"
            5 -> "금"
            6 -> "토"
            7 -> "일"
            else -> throw IllegalArgumentException()
        }
    }

    fun LocalDateTime.isNotYet(): Boolean {
        val now = LocalDateTime.now().toLocalDateTime(timeZone)
        return compareTo(now) < 0
    }

    fun LocalDateTime.getPeriodString(): String {
        val now = LocalDateTime.now()
        val period = this.toInstant(timeZone).periodUntil(now, timeZone)
        return "${period.hours.toFormattedPeriod()}:${period.minutes.toFormattedPeriod()}:${period.seconds.toFormattedPeriod()}"
    }

    fun LocalDateTime.daysUntilNow(): Int {
        val today = LocalDateTime.now().toLocalDateTime(timeZone)
        return date.daysUntil(today.date)
    }

    fun LocalDateTime.getDdayString(): String {
        if (isToday()) return "D-day"
        val diffInDays = daysUntilNow()
        return "D" + if (diffInDays > 0) {
            "+$diffInDays"
        } else if (diffInDays < 0) {
            diffInDays.toString()
        } else {
            "-day"
        }
    }

    fun LocalDateTime.isToday(): Boolean {
        val today = LocalDateTime.now().toLocalDateTime(timeZone)
        return date == today.date
    }

    fun LocalDateTime.getMonthDayString(): String = format("M월 d일")

    fun LocalDateTime.getTimeString(): String = format("HH:mm")

    fun LocalDateTime.Companion.now() = Clock.System.now()

    private fun Int.toFormattedPeriod() =
        abs(this).toString().padStart(2, '0')
}

expect fun LocalDateTime.format(pattern: String): String