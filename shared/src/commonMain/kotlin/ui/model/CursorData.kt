package ui.model

import ui.model.CursorRequest.Companion.DEFAULT_LIMIT

data class CursorData<T> (
    val data: List<T> = emptyList(),
    val nextCursorId: Int? = null,
    val isLast: Boolean? = null
) {
    fun nextRequest(limit: Int = DEFAULT_LIMIT) = if (isLast == true) {
        null
    } else {
        CursorRequest(nextCursorId, limit)
    }

    fun concatenate(next: CursorData<T>) = CursorData(
        data = data + next.data,
        nextCursorId = next.nextCursorId,
        isLast = next.isLast
    )
}
