import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

/**
 * For supporting coroutines
 * [Flow] on both platforms.
 */
expect class CommonFlow<T>(flow: Flow<T>) : Flow<T>

fun <T> Flow<T>.toCommonFlow() = CommonFlow(this)

/**
 * For supporting coroutines
 * [StateFlow] on both platforms.
 */
expect open class CommonStateFlow<T>(flow: StateFlow<T>) : StateFlow<T>

fun <T> StateFlow<T>.toCommonStateFlow() = CommonStateFlow(this)
