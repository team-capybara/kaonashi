package di

import org.koin.core.context.startKoin

fun initKoin() = startKoin {
    modules(networkModule)
    modules(settingsModule)
    modules(hazeModule)
}
