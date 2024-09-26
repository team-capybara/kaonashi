package di

import org.koin.dsl.module
import ui.friend.FriendScreenModel
import ui.login.LoginScreenModel
import ui.main.MainScreenModel
import ui.main.home.HomeScreenModel
import ui.main.insight.InsightScreenModel

val screenModelModule = module {
    single { MainScreenModel(get()) }
    single { LoginScreenModel(get()) }
    single { HomeScreenModel(get()) }
    single { FriendScreenModel(get()) }
    single { InsightScreenModel() }
}
