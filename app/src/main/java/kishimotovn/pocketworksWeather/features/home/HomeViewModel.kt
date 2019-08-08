package kishimotovn.pocketworksWeather.features.home

import androidx.lifecycle.*
import kishimotovn.pocketworksWeather.data.local.models.PWCity
import kishimotovn.pocketworksWeather.data.local.models.PWUnitSystem
import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: HomeRepository): ViewModel() {
    val isLoading = MutableLiveData(false)
    val userCityWeather = MediatorLiveData<List<CityWeather>>()
    val currentUnitSystem: PWUnitSystem
        get() {
            return this.repository.getUnitSystem()
        }
    val unitSystem = MutableLiveData(this.currentUnitSystem)

    fun fetchWeatherDataForMyCities() {
        this.isLoading.value = true

        this.userCityWeather.addSource(this.repository.getUserCityWeather()) {
            this.isLoading.value = false
            this.userCityWeather.value = it
        }
    }

    fun changeUnitSystem(newUnitSystem: PWUnitSystem) {
        if (this.currentUnitSystem == newUnitSystem) {
            return
        }

        this.repository.setUnitSystem(newUnitSystem)
        this.unitSystem.value = newUnitSystem
    }

    fun addCity(city: PWCity) {
        this.repository.insertIfNeeded(city.id)
        this.fetchWeatherDataForMyCities()
    }

    fun removeCity(cityId: String) {
        this.userCityWeather.value?.let { currentData ->
            currentData.indexOfFirst { it.id == cityId.toIntOrNull() }?.let {
                val newUserCityList = currentData.toMutableList()
                newUserCityList.removeAt(it)
                this.repository.removeIfNeeded(cityId)

                this.userCityWeather.value = newUserCityList
            }
        }
    }
}