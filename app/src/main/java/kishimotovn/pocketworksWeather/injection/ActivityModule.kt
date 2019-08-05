package kishimotovn.pocketworksWeather.injection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kishimotovn.pocketworksWeather.features.home.HomeActivity
import kishimotovn.pocketworksWeather.features.landing.LandingActivity
import kishimotovn.pocketworksWeather.features.search.SearchActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeLandingActivity(): LandingActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSearchActivity(): SearchActivity

    @ContributesAndroidInjector
    internal abstract fun contributeHomeActivity(): HomeActivity
}