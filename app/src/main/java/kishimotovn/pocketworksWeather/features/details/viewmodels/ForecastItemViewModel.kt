package kishimotovn.pocketworksWeather.features.details.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import kishimotovn.pocketworksWeather.data.local.models.PWUnitSystem
import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import kishimotovn.pocketworksWeather.utils.PWFormatter
import java.text.SimpleDateFormat
import java.util.*

class ForecastItemViewModel(cityWeather: CityWeather, unit: PWUnitSystem, timezoneOffset: Int): ViewModel() {
    private val timeFormatter by lazy {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val localTimeZone = TimeZone.getTimeZone("UTC")
        localTimeZone.rawOffset = timezoneOffset * 1000
        formatter.timeZone = localTimeZone
        formatter
    }
    var time = ObservableField(timeFormatter.format(cityWeather.dt ?: Date()))
    var windInfo = ObservableField(
            PWFormatter.windInfoString(
                    cityWeather.wind?.deg ?: 0.0,
                    cityWeather.wind?.speed ?: 0.0,
                    unit))
    var icon = ObservableField("http://openweathermap.org/img/wn/${cityWeather.weather?.firstOrNull()?.icon ?: "10d"}@2x.png")
}