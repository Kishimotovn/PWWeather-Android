package kishimotovn.pocketworksWeather.data.remote.models

import com.squareup.moshi.Json
import java.util.*


data class CityWeatherList(
        @field:Json(name = "cnt") val cnt: Int,
        @field:Json(name = "list") val list: List<CityWeather>
)

data class CityWeather(
        @field:Json(name = "coord") val coord: CityCoord?,
        @field:Json(name = "weather") val weather: List<Weather>?,
        @field:Json(name = "main") val main: MainWeather?,
        @field:Json(name = "visibility") val visibility: Double?,
        @field:Json(name = "wind") val wind: Wind?,
        @field:Json(name = "clouds") val clouds: Clouds?,
        @field:Json(name = "dt") val dt: Date?,
        @field:Json(name = "sys") val sys: WeatherSys?,
        @field:Json(name = "id") val id: Int?,
        @field:Json(name = "name") val name: String?
        )

data class CityCoord(
        @field:Json(name = "lat") val lat: Double?,
        @field:Json(name = "lon") val lon: Double?
)

data class Weather(
        @field:Json(name = "id") val id: Int?,
        @field:Json(name = "main") val main: String?,
        @field:Json(name = "description") val description: String?,
        @field:Json(name = "icon") val icon: String?
)

data class MainWeather(
        @field:Json(name = "temp") val temp: Double?,
        @field:Json(name = "pressure") val pressure: Double?,
        @field:Json(name = "humidity") val humidity: Double?,
        @field:Json(name = "temp_min") val tempMin: Double?,
        @field:Json(name = "temp_max") val tempMax: Double?
)

data class Clouds(
        @field:Json(name = "all") val all: Int?)

data class Wind(
        @field:Json(name = "speed") val speed: Double?,
        @field:Json(name = "deg") val deg: Double?
)

data class WeatherSys(
        @field:Json(name = "country") val country: String?,
        @field:Json(name = "timezone") val timezone: Int?,
        @field:Json(name = "sunrise") val sunrise: Date?,
        @field:Json(name = "sunset") val sunset: Date?
)
