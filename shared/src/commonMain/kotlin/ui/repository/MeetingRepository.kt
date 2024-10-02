package ui.repository

import kotlinx.datetime.LocalDate
import ui.model.CursorData
import ui.model.CursorRequest
import ui.model.Meeting

interface MeetingRepository {
    suspend fun getAllMeetings(): Result<List<Meeting>>

    suspend fun getAllUpcomingMeetings(): Result<List<Meeting>>

    suspend fun getAllOngoingMeetings(): Result<List<Meeting>>

    suspend fun getAllCompletedMeetings(): Result<List<Meeting>>

    suspend fun getCompletedMeetings(cursor: CursorRequest): Result<CursorData<Meeting>>

    suspend fun getOngoingMeetings(cursor: CursorRequest): Result<CursorData<Meeting>>

    suspend fun getUpcomingMeetings(cursor: CursorRequest): Result<CursorData<Meeting>>

    suspend fun getMeetingsCount(from: LocalDate, to: LocalDate): Result<Map<LocalDate, Int>>

    suspend fun getMeetingsOfDay(date: LocalDate): Result<List<Meeting>>
}
