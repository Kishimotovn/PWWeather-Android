package kishimotovn.pocketworksWeather.data.remote

import kishimotovn.pocketworksWeather.data.remote.models.CityWeatherList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {
    @GET("data/2.5/group")
    fun getWeatherDataForCities(@Query("id") cityIds: String): Call<CityWeatherList>

    @GET("data/2.5/weather")
    fun getWeatherDataForCity(@Query("id") cityId: String): Call<CityWeatherList>
}