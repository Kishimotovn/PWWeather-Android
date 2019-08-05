package kishimotovn.pocketworksWeather.features.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import kishimotovn.pocketworksWeather.data.remote.DataService
import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import kishimotovn.pocketworksWeather.utils.ApiEmptyResponse
import kishimotovn.pocketworksWeather.utils.ApiErrorResponse
import kishimotovn.pocketworksWeather.utils.ApiSuccessResponse
import kishimotovn.pocketworksWeather.utils.PreferencesManager
import javax.inject.Inject

class HomeRepository @Inject constructor(val context: Context, val prefManager: PreferencesManager, val dataService: DataService) {
    fun getUserCityWeather(): LiveData<List<CityWeather>> {
        var cityIds = this@HomeRepository.prefManager.getString("userCity", "") ?: ""

        cityIds = "524901,703448,2643743"
        if (cityIds.isEmpty()) {
            return liveData { emit(listOf<CityWeather>()) }
        } else {
            return this@HomeRepository.dataService.getWeatherDataForCities(cityIds).map {
                when(it) {
                    is ApiSuccessResponse -> (it.body.list)
                    else -> listOf()
                }
            }
        }
    }
}
