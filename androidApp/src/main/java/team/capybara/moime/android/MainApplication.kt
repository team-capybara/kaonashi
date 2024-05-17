package team.capybara.moime.android

import android.app.Application
import di.initKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
