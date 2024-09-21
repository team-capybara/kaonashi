package ui.repository

import ui.model.Meeting

interface MeetingRepository {
    suspend fun getAllMeetings(): Result<List<Meeting>>
}
