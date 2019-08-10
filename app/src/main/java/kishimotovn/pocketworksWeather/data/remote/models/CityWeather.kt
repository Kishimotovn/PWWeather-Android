package kishimotovn.pocketworksWeather.data.remote.models

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import java.util.*

@JsonClass(generateAdapter = true)
data class CityWeatherList(
        @field:Json(name = "cnt") val cnt: Int,
        @field:Json(name = "list") val list: List<CityWeather>
)

@JsonClass(generateAdapter = true)
@Parcelize
data class CityWeather(
        val coord: CityCoord?,
        val weather: List<Weather>?,
        val main: MainWeather?,
        val visibility: Double?,
        val wind: Wind?,
        val clouds: Clouds?,
        val dt: Date?,
        val sys: WeatherSys?,
        val id: Int?,
        val name: String?
        ): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class CityCoord(
        val lat: Double?,
        val lon: Double?
): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Weather(
        val id: Int?,
        val main: String?,
        val description: String?,
        val icon: String?
): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class MainWeather(
        val temp: Double?,
        val pressure: Double?,
        val humidity: Double?,
        @field:Json(name = "temp_min") val tempMin: Double?,
        @field:Json(name = "temp_max") val tempMax: Double?
): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Clouds(val all: Int?): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class Wind(
        val speed: Double?,
        val deg: Double?
): Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
data class WeatherSys(
        val country: String?,
        val timezone: Int?,
        val sunrise: Date?,
        val sunset: Date?
): Parcelable
