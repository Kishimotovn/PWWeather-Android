package kishimotovn.pocketworksWeather.features.details

import android.text.Spanned
import androidx.databinding.ObservableField
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kishimotovn.pocketworksWeather.data.local.models.PWUnitSystem
import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import kishimotovn.pocketworksWeather.utils.PWFormatter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DetailsViewModel @Inject constructor(private val repository: DetailsRepository): ViewModel() {
    var cityName = ObservableField<String>()
    var cityMainWeather = ObservableField<String>()
    var windInfo = ObservableField<Spanned>()
    var weekDay = ObservableField<String>()
    var maxTemp = ObservableField<String>()
    var minTemp = ObservableField<String>()
    var weatherDescription = ObservableField<String>()
    var temperature = ObservableField<String>()
    var humidity = ObservableField<String>()
    var sunrise = ObservableField<String>()
    var sunset = ObservableField<String>()
    var pressure = ObservableField<String>()
    var visibility = ObservableField<String>()
    var isLoadingForecast = MutableLiveData(true)

    var cityWeather: CityWeather? = null
    val forecastWeatherData = MediatorLiveData<List<CityWeather>>()
    val currentUnitSystem = this.repository.getUnitSystem()

    fun loadForecast() {
        this.isLoadingForecast.value = true
        this.cityWeather?.id?.toString()?.let {
            forecastWeatherData.addSource(this.repository.getCityForecastData(it)) { forecastData ->
                this.isLoadingForecast.value = false
                this.forecastWeatherData.value = forecastData
            }
        }
    }

    fun loadCityWeather(cityWeather: CityWeather) {
        this.cityWeather = cityWeather
        val currentUnitSystem = this.repository.getUnitSystem()

        this.cityName.set(cityWeather.name)
        this.cityMainWeather.set(cityWeather.weather?.firstOrNull()?.main)
        this.windInfo.set(PWFormatter.windInfoString(
                cityWeather.wind?.deg ?: 0.0,
                cityWeather.wind?.speed ?: 0.0,
                currentUnitSystem
        ))
        this.weekDay.set(this.timeFormatter("EEEE", (cityWeather.sys?.timezone ?: 0) * 1000).format(Date()))
        this.maxTemp.set(PWFormatter.temperatureString(cityWeather.main?.tempMax ?: 0.0, currentUnitSystem).dropLast(1))
        this.minTemp.set(PWFormatter.temperatureString(cityWeather.main?.tempMin ?: 0.0, currentUnitSystem).dropLast(1))
        val timeFormatter = this.timeFormatter("HH:mm", (cityWeather.sys?.timezone ?: 0) * 1000)

        this.weatherDescription.set(this.buildWeatherDescription(cityWeather, currentUnitSystem))

        this.temperature.set(PWFormatter.temperatureString(cityWeather.main?.temp ?: 0.0, currentUnitSystem))
        this.humidity.set("${cityWeather.main?.humidity ?: 0}%")
        this.sunrise.set(timeFormatter.format(cityWeather.sys?.sunrise ?: 0))
        this.sunset.set(timeFormatter.format(cityWeather.sys?.sunset ?: 0))
        this.pressure.set(PWFormatter.pressureString(cityWeather.main?.pressure ?: 0.0))
        this.visibility.set(PWFormatter.lengthString(cityWeather.visibility ?: 0.0, currentUnitSystem))
    }

    private fun timeFormatter(format: String, timezoneOffset: Int): DateFormat {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        val localTimeZone = TimeZone.getTimeZone("UTC")
        localTimeZone.rawOffset = timezoneOffset
        formatter.timeZone = localTimeZone
        return formatter
    }

    private fun buildWeatherDescription(cityWeather: CityWeather, currentUnitSystem: PWUnitSystem): String {
        val mainDescription = cityWeather.weather?.firstOrNull()?.description ?: "N/A"
        val currentTemperature = PWFormatter.temperatureString(cityWeather.main?.temp ?: 0.0, currentUnitSystem)
        val currentMaxTemperature = PWFormatter.temperatureString(cityWeather.main?.tempMax ?: 0.0, currentUnitSystem)
        return "Today: $mainDescription. It's $currentTemperature; the high today was forecast as $currentMaxTemperature"
    }
}