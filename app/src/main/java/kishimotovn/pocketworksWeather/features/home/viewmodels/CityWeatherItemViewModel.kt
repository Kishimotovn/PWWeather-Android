package kishimotovn.pocketworksWeather.features.home.viewmodels

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import kishimotovn.pocketworksWeather.data.remote.models.CityWeather
import java.text.SimpleDateFormat
import java.util.*

class CityWeatherItemViewModel(cityWeather: CityWeather): ViewModel() {
    val time = ObservableField("")
    val cityName = ObservableField(cityWeather.name ?: "N/A")
    val windInformation = ObservableField("WWE 2 m/s")

    private val mainHandler by lazy { Handler(Looper.getMainLooper()) }
    private val timeFormatter by lazy {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val localTimeZone = TimeZone.getTimeZone("UTC")
//        localTimeZone.rawOffset = (cityWeather.sys?.timezone ?: 0) * 1000
        Log.d("CityWeatherItemViewMode","This city ${cityWeather.name} offset: ${cityWeather.sys?.timezone}")
        formatter.timeZone = localTimeZone
        formatter
    }

    init {
        mainHandler.post(object: Runnable {
            override fun run() {
                val currentTime = Date()
                val timeString = this@CityWeatherItemViewModel.timeFormatter.format(currentTime)
                this@CityWeatherItemViewModel.time.set(timeString)
                this@CityWeatherItemViewModel.mainHandler.postDelayed(this, 1000)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        this.mainHandler.removeCallbacksAndMessages(null)
    }
}