package kishimotovn.pocketworksWeather.features.home.viewmodels

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import kishimotovn.pocketworksWeather.data.local.models.PWUnitSystem
import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import kishimotovn.pocketworksWeather.utils.PWFormatter
import java.text.SimpleDateFormat
import java.util.*

class CityWeatherItemViewModel(cityWeather: CityWeather, unit: PWUnitSystem): ViewModel() {
    val time = ObservableField("")
    val cityName = ObservableField(cityWeather.name ?: "N/A")
    val windInformation = ObservableField(
            PWFormatter.windInfoString(
                    cityWeather.wind?.deg ?: 0.0,
                    cityWeather.wind?.speed ?: 0.0,
                    unit))

    private val mainHandler by lazy { Handler(Looper.getMainLooper()) }
    private val timeFormatter by lazy {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val localTimeZone = TimeZone.getTimeZone("UTC")
        localTimeZone.rawOffset = (cityWeather.sys?.timezone ?: 0) * 1000
        formatter.timeZone = localTimeZone
        formatter
    }

    init {
        mainHandler.post(object: Runnable {
            override fun run() {
                val currentTime = Date()
                val timeString = this@CityWeatherItemViewModel.timeFormatter.format(currentTime)
                this@CityWeatherItemViewModel.time.set(timeString)
                this@CityWeatherItemViewModel.mainHandler.postDelayed(this, 100)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        this.mainHandler.removeCallbacksAndMessages(null)
    }
}