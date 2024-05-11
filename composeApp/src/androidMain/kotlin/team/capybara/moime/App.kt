package team.capybara.moime

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        di.initKoin()
    }
}
