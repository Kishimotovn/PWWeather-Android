package kishimotovn.pocketworksWeather

import androidx.multidex.MultiDexApplication
import kishimotovn.pocketworksWeather.injection.AppComponent
import kishimotovn.pocketworksWeather.injection.AppModule
import kishimotovn.pocketworksWeather.injection.DaggerAppComponent
import kishimotovn.pocketworksWeather.injection.NetworkModule

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