import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual open class CommonFlow<T> actual constructor(
    private val flow: Flow<T>
) : Flow<T> by flow {
    fun collect(
        onCollect: (T) -> Unit
    ): DisposableHandle {
        val job = CoroutineScope(Dispatchers.Main).launch {
            flow.collect(onCollect)
        }
        return DisposableHandle { job.cancel() }
    }
}

actual open class CommonStateFlow<T> actual constructor(
    private val flow: StateFlow<T>
) : CommonFlow<T>(flow), StateFlow<T> {

    override val replayCache: List<T>
        get() = flow.replayCache

    override val value: T
        get() = flow.value

    override suspend fun collect(collector: FlowCollector<T>) = flow.collect(collector)
}
