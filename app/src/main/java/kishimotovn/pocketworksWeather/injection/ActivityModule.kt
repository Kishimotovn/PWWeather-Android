package kishimotovn.pocketworksWeather.injection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kishimotovn.pocketworksWeather.features.landing.LandingActivity

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeLandingActivity(): LandingActivity
}