package com.template.app

import androidx.multidex.MultiDexApplication
import com.template.app.injection.AppComponent
import com.template.app.injection.AppModule
import com.template.app.injection.DaggerAppComponent
import com.template.app.injection.NetworkModule

class Application: MultiDexApplication() {

    private lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule(this))
                .build()
    }

    fun getMyComponent(): AppComponent {
        return appComponent
    }
}