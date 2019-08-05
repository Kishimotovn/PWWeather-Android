package kishimotovn.pocketworksWeather.features.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kishimotovn.pocketworksWeather.data.remote.DataService
import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository, val dataService: DataService): ViewModel() {
    val isLoading = MutableLiveData<Boolean>(false)
    val userCityWeather = MediatorLiveData<List<CityWeather>>()

    fun fetchWeatherDataForMyCities() {
        this.isLoading.value = true

        this.userCityWeather.addSource(this.repository.getUserCityWeather()) {
            this.isLoading.value = false
            this.userCityWeather.value = it
        }
    }
}