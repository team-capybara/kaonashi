package di

import data.repository.FriendRepositoryImpl
import org.koin.dsl.module
import ui.repository.FriendRepository

val repositoryModule = module {
    single<FriendRepository> { FriendRepositoryImpl(get()) }
}
