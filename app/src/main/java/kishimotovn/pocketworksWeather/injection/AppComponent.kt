package kishimotovn.pocketworksWeather.injection

import android.content.Context
import kishimotovn.pocketworksWeather.Application
import kishimotovn.pocketworksWeather.features.landing.LandingActivity
import kishimotovn.pocketworksWeather.data.remote.RemoteRepository
import kishimotovn.pocketworksWeather.utils.PreferencesManager
import dagger.Component
import kishimotovn.pocketworksWeather.features.details.DetailsActivity
import kishimotovn.pocketworksWeather.features.home.HomeActivity
import kishimotovn.pocketworksWeather.features.search.SearchActivity
import javax.inject.Singleton

@Singleton
@Component(modules  = [AppModule::class, NetworkModule::class, ActivityModule::class, ViewModelModule::class])
interface AppComponent {

    fun context(): Context
    fun application(): Application
    fun preferencesManager(): PreferencesManager
    fun repository(): RemoteRepository

    fun inject(landingActivity: LandingActivity)
    fun inject(searchActivity: SearchActivity)
    fun inject(homeActivity: HomeActivity)
    fun inject(detailsActivity: DetailsActivity)
}