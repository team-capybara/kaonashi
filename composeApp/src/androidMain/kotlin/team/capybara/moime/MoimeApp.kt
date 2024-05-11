package team.capybara.moime

import android.app.Application

class MoimeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        di.initKoin()
    }
}
