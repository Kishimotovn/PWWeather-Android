package kishimotovn.pocketworksWeather.data.remote

import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine


class RemoteRepository @Inject constructor(private var dataService: DataService) {
    suspend fun getWeatherDataForCities(cityIds: List<String>): List<CityWeather> {
        return suspendCoroutine { continuation ->  
            val response = dataService.getWeatherDataForCities(cityIds.joinToString(",")).execute()

            response.body()?.list?.let {
                continuation.resumeWith(Result.success(it))
            } ?: run {
                continuation.resumeWith(Result.failure(Exception(response.errorBody()?.string() ?: "Failed to get weather data")))
            }
        }
    }
}