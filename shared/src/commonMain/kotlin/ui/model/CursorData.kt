package ui.model

import ui.model.CursorRequest.Companion.DEFAULT_LIMIT

data class CursorData<T>(
    val data: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val nextCursorId: Int? = null,
    val isLast: Boolean? = null
) {
    fun canRequest() = !isLoading && isLast != true

    fun nextRequest(limit: Int = DEFAULT_LIMIT) = CursorRequest(nextCursorId, limit)

    fun loading(value: Boolean = true) = copy(isLoading = value)

    fun concatenate(next: CursorData<T>) = copy(
        data = data + next.data,
        isLoading = false,
        nextCursorId = next.nextCursorId,
        isLast = next.isLast
    )
}
