package di

import dev.chrisbanes.haze.HazeState
import org.koin.dsl.module

val hazeModule = module {
    single { HazeState() }
}
