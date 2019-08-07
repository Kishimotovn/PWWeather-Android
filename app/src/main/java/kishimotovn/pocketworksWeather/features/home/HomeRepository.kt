package kishimotovn.pocketworksWeather.features.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kishimotovn.pocketworksWeather.data.local.models.PWUnitSystem
import kishimotovn.pocketworksWeather.data.remote.RemoteRepository
import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import kishimotovn.pocketworksWeather.utils.PreferencesManager
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class HomeRepository @Inject constructor(val context: Context, val prefManager: PreferencesManager, private val remoteRepository: RemoteRepository) {
    fun insertIfNeeded(cityId: String): Boolean {
        val cityIds = this.getCityIds().toMutableList()
        return if (cityIds.contains(cityId)) {
            false
        } else {
            cityIds.add(0, cityId)
            val cityString = cityIds.joinToString(",")
            this.prefManager.setString("userCities", cityString)
            true
        }
    }

    fun getUserCityWeather() = liveData(context = Dispatchers.IO) {
        val cityIds = this@HomeRepository.getCityIds()

        if (cityIds.isEmpty()) {
            emit(listOf<CityWeather>())
        } else {
            val cityWeatherData = this@HomeRepository.remoteRepository.getWeatherDataForCities(cityIds = cityIds)
            emit(cityWeatherData)
        }
    }

    fun getUnitSystem(): PWUnitSystem {
        val unitString = this.prefManager.getString("unitSystem", "")
        return try {
            PWUnitSystem.valueOf(unitString ?: "metric")
        } catch(e: Throwable) {
            PWUnitSystem.metric
        }
    }

    fun setUnitSystem(newUnitSystem: PWUnitSystem) {
        this.prefManager.setString("unitSystem", newUnitSystem.name)
    }

    private fun getCityIds(): List<String> {
        var cityIdsString = this@HomeRepository.prefManager.getString("userCities", "") ?: ""
        return cityIdsString.split(",").filterNot { it.isEmpty() }
    }
}
