package kishimotovn.pocketworksWeather.features.details

import androidx.lifecycle.liveData
import kishimotovn.pocketworksWeather.data.local.models.PWUnitSystem
import kishimotovn.pocketworksWeather.data.remote.RemoteRepository
import kishimotovn.pocketworksWeather.utils.PreferencesManager
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val remoteRepository: RemoteRepository, private val prefManager: PreferencesManager) {
    fun getCityForecastData(cityId: String) = liveData(context = Dispatchers.IO) {
        val forecastWeatherData = this@DetailsRepository.remoteRepository.getWeatherForecastDataForCity(cityId)
        emit(forecastWeatherData)
    }

    fun getUnitSystem(): PWUnitSystem {
        val unitString = this.prefManager.getString("unitSystem", "")
        return try {
            PWUnitSystem.valueOf(unitString ?: "metric")
        } catch(e: Throwable) {
            PWUnitSystem.metric
        }
    }
}
