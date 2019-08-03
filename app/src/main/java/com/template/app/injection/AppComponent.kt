package com.template.app.injection

import android.content.Context
import com.template.app.Application
import com.template.app.MainActivity
import com.template.app.data.remote.Repository
import com.template.app.utils.PreferencesManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules  = [AppModule::class, NetworkModule::class])
interface AppComponent {

    fun context(): Context
    fun application(): Application
    fun preferencesManager(): PreferencesManager
    fun repository(): Repository

    fun inject(mainActivity: MainActivity)

}