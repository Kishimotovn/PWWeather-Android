package kishimotovn.pocketworksWeather.data.remote

import androidx.lifecycle.LiveData
import kishimotovn.pocketworksWeather.data.remote.models.CityWeatherList
import kishimotovn.pocketworksWeather.utils.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {
    @GET("data/2.5/group")
    fun getWeatherDataForCities(@Query("id") cityIds: String): LiveData<ApiResponse<CityWeatherList>>

    @GET("data/2.5/weather")
    fun getWeatherDataForCity(@Query("id") cityId: String): LiveData<ApiResponse<CityWeatherList>>
}