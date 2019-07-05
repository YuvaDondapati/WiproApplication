package yuva.assignment.wiproapplication.app

import android.app.Application

import yuva.assignment.wiproapplication.diprovider.ApiModule
import yuva.assignment.wiproapplication.diprovider.AppModule

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        netComponent = DaggerApiComponent.builder()
                .appModule(AppModule(this))
                .apiModule(ApiModule(Constant.BASE_URL))
                .build()
    }

    companion object {
        var netComponent: ApiComponent? = null
            private set
        @get:Synchronized
        var instance: MyApplication? = null
            private set
    }
}