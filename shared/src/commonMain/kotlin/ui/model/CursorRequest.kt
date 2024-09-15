package ui.model

data class CursorRequest(
    val cursorId: Int?,
    val limit: Int? = DEFAULT_LIMIT
) {
    companion object {
        const val DEFAULT_LIMIT = 10
    }
}
