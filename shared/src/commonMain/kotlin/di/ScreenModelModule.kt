package di

import org.koin.dsl.module
import ui.main.MainScreenModel

val screenModelModule = module {
    single {
        MainScreenModel()
    }
}
