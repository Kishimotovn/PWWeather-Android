package kishimotovn.pocketworksWeather.features.search

import android.content.Context
import androidx.lifecycle.map
import kishimotovn.pocketworksWeather.data.local.DAOs.PWCityDao
import javax.inject.Inject

class SearchRepository @Inject constructor(val context: Context, val cityDao: PWCityDao) {
    fun getCities(searchTerm: String) = cityDao.searchCity(searchTerm).map {
        Pair(searchTerm, it)
    }
}