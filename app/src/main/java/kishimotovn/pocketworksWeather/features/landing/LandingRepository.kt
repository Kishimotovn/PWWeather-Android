package kishimotovn.pocketworksWeather.features.landing

import android.content.Context
import androidx.lifecycle.LiveData
import kishimotovn.pocketworksWeather.R
import kishimotovn.pocketworksWeather.data.local.DAOs.PWCityDao
import kishimotovn.pocketworksWeather.data.local.models.PWCity
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list
import javax.inject.Inject

class LandingRepository @Inject constructor(val context: Context, val cityDao: PWCityDao) {
    fun getCitiesFromDB(): LiveData<List<PWCity>> {
        return this.cityDao.getCities()
    }

    fun parseAndSaveToDB(): List<PWCity> {
        val inputStream = this@LandingRepository.context.resources.openRawResource(R.raw.city_list)
        val cityJSONString = inputStream.bufferedReader().use { it.readText() }
        val json = Json(JsonConfiguration.Stable)
        val cities = json.parse(PWCity.serializer().list, cityJSONString)
        this@LandingRepository.cityDao.insertCities(cities)
        return cities
    }
}