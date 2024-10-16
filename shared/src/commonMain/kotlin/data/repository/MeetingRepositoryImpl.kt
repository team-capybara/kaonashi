package data.repository

import data.Api
import data.model.ApiException
import data.model.MeetingCountPerMonthResponse
import data.model.MeetingCountResponse
import data.model.MeetingDateResponse
import data.model.MeetingResponse
import data.util.DateUtil.toApiFormat
import data.util.DateUtil.toIsoDateTimeFormat
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.plus
import ui.model.CursorData
import ui.model.CursorRequest
import ui.model.Meeting
import ui.repository.MeetingRepository

class MeetingRepositoryImpl(
    private val httpClient: HttpClient
) : MeetingRepository {

    override suspend fun getAllMeetings(): Result<List<Meeting>> = runCatching {
        listOf(
            Api.MOIMS_UPCOMING,
            Api.MOIMS_TODAY,
            Api.MOIMS_COMPLETE
        ).fold<String, List<Meeting>>(emptyList()) { acc, api ->
            acc + httpClient.get(api) {
                url {
                    parameters.append("size", "100")
                }
            }.body<MeetingResponse>().data.map { it.toUiModel() }
        }.sortedBy { it.startDateTime }
    }

    override suspend fun getAllUpcomingMeetings(): Result<List<Meeting>> =
        runCatching {
            httpClient.get(Api.MOIMS_UPCOMING) {
                url {
                    parameters.append("size", DEFAULT_SIZE_OF_PAGE.toString())
                }
            }.run {
                if (status.value != 200) {
                    throw ApiException(status)
                } else {
                    body<MeetingResponse>().data.map { it.toUiModel() }
                }
            }
        }

    override suspend fun getAllOngoingMeetings(): Result<List<Meeting>> =
        runCatching {
            httpClient.get(Api.MOIMS_TODAY) {
                url {
                    parameters.append("size", DEFAULT_SIZE_OF_PAGE.toString())
                }
            }.run {
                if (status.value != 200) {
                    throw ApiException(status)
                } else {
                    body<MeetingResponse>().data.map { it.toUiModel() }
                }
            }
        }

    override suspend fun getCompletedMeetings(cursor: CursorRequest): Result<CursorData<Meeting>> =
        runCatching {
            val response = httpClient.get(Api.MOIMS_COMPLETE) {
                url {
                    with(cursor) {
                        cursorId?.let { parameters.append("cursor.cursorMoimId", it.toString()) }
                        cursorDate?.let { parameters.append("cursor.cursorDate", it.toApiFormat()) }
                        limit?.let { parameters.append("size", it.toString()) }
                    }
                }
            }
            if (response.status.value != 200) throw ApiException(response.status)
            response.body<MeetingResponse>().run {
                CursorData(
                    data = data.map { it.toUiModel() },
                    nextCursorId = cursorId?.cursorMoimId,
                    nextCursorDate = cursorId?.cursorDate?.let { LocalDateTime.parse(it.toIsoDateTimeFormat()) },
                    isLast = last
                )
            }
        }

    override suspend fun getOngoingMeetings(cursor: CursorRequest): Result<CursorData<Meeting>> =
        runCatching {
            val response = httpClient.get(Api.MOIMS_TODAY) {
                url {
                    with(cursor) {
                        cursorId?.let { parameters.append("cursor.cursorMoimId", it.toString()) }
                        cursorDate?.let { parameters.append("cursor.cursorDate", it.toApiFormat()) }
                        limit?.let { parameters.append("size", it.toString()) }
                    }
                }
            }
            if (response.status.value != 200) throw ApiException(response.status)
            response.body<MeetingResponse>().run {
                CursorData(
                    data = data.map { it.toUiModel() },
                    nextCursorId = cursorId?.cursorMoimId,
                    nextCursorDate = cursorId?.cursorDate?.let { LocalDateTime.parse(it.toIsoDateTimeFormat()) },
                    isLast = last
                )
            }
        }

    override suspend fun getAllCompletedMeetings(): Result<List<Meeting>> =
        runCatching {
            httpClient.get(Api.MOIMS_COMPLETE) {
                url {
                    parameters.append("size", DEFAULT_SIZE_OF_PAGE.toString())
                }
            }.run {
                if (status.value != 200) {
                    throw ApiException(status)
                } else {
                    body<MeetingResponse>().data.map { it.toUiModel() }
                }
            }
        }

    override suspend fun getUpcomingMeetings(cursor: CursorRequest): Result<CursorData<Meeting>> =
        runCatching {
            val response = httpClient.get(Api.MOIMS_UPCOMING) {
                url {
                    with(cursor) {
                        cursorId?.let { parameters.append("cursor.cursorMoimId", it.toString()) }
                        cursorDate?.let { parameters.append("cursor.cursorDate", it.toApiFormat()) }
                        limit?.let { parameters.append("size", it.toString()) }
                    }
                }
            }
            if (response.status.value != 200) throw ApiException(response.status)
            response.body<MeetingResponse>().run {
                CursorData(
                    data = data.map { it.toUiModel() },
                    nextCursorId = cursorId?.cursorMoimId,
                    nextCursorDate = cursorId?.cursorDate?.let { LocalDateTime.parse(it.toIsoDateTimeFormat()) },
                    isLast = last
                )
            }
        }

    override suspend fun getMeetingsCount(
        from: LocalDate,
        to: LocalDate
    ): Result<Map<LocalDate, Int>> =
        runCatching {
            val meetingsCount = mutableMapOf<LocalDate, Int>()
            var targetDate = from
            while (targetDate <= to) {
                httpClient.get(Api.MOIMS_CALENDAR) {
                    url {
                        parameters.append("year", targetDate.year.toString())
                        parameters.append("month", targetDate.monthNumber.toString())
                    }
                }.run {
                    if (status.value != 200) {
                        throw ApiException(status)
                    } else {
                        meetingsCount += body<MeetingCountResponse>().parse()
                    }
                }
                targetDate = targetDate.plus(DatePeriod(months = 1))
            }
            meetingsCount
        }

    override suspend fun getMeetingsOfDay(date: LocalDate): Result<List<Meeting>> =
        runCatching {
            httpClient.get(Api.MOIMS_DATE) {
                url {
                    parameters.append("date", date.toApiFormat())
                }
            }.run {
                if (status.value != 200) {
                    throw ApiException(status)
                } else {
                    body<MeetingDateResponse>().data.map { it.toUiModel() }
                }
            }
        }

    override suspend fun getMeetingsWith(
        targetId: Long,
        cursor: CursorRequest
    ): Result<CursorData<Meeting>> =
        runCatching {
            val response = httpClient.get(Api.MOIMS_WITH(targetId)) {
                url {
                    with(cursor) {
                        cursorId?.let { parameters.append("cursorMoimId", it.toString()) }
                        limit?.let { parameters.append("size", it.toString()) }
                    }
                }
            }
            if (response.status.value != 200) throw ApiException(response.status)
            response.body<MeetingResponse>().run {
                CursorData(
                    data = data.map { it.toUiModel() },
                    nextCursorId = cursorId?.cursorMoimId,
                    nextCursorDate = null,
                    isLast = last
                )
            }
        }

    override suspend fun getMeetingsCountWith(targetId: Long): Result<Int> = runCatching {
        httpClient.get(Api.MOIMS_WITH_COUNT(targetId)).run {
            if (status.value != 200) {
                throw ApiException(status)
            } else {
                body<MeetingCountPerMonthResponse>().count
            }
        }
    }

    companion object {
        private const val DEFAULT_SIZE_OF_PAGE = 20
    }
}
