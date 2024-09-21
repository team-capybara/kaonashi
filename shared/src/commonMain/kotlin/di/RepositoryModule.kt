package di

import data.repository.FriendRepositoryImpl
import data.repository.MeetingRepositoryImpl
import data.repository.UserRepositoryImpl
import org.koin.dsl.module
import ui.repository.FriendRepository
import ui.repository.MeetingRepository
import ui.repository.UserRepository

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<MeetingRepository> { MeetingRepositoryImpl(get()) }
    single<FriendRepository> { FriendRepositoryImpl(get()) }
}
