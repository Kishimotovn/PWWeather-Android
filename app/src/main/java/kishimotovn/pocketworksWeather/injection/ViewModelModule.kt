package kishimotovn.pocketworksWeather.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kishimotovn.pocketworksWeather.factory.AppViewModelFactory
import kishimotovn.pocketworksWeather.features.home.HomeViewModel
import kishimotovn.pocketworksWeather.features.landing.LandingViewModel
import kishimotovn.pocketworksWeather.features.search.SearchViewModel

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LandingViewModel::class)
    internal abstract fun bindLandingViewModel(landingViewModel: LandingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(appViewModelFactory: AppViewModelFactory) : ViewModelProvider.Factory
}