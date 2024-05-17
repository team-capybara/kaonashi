package team.capybara.moime

import Notifier
import android.app.Application
import di.initKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        Notifier.initialize()
    }
}
