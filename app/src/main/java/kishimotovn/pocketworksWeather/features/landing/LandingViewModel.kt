package kishimotovn.pocketworksWeather.features.landing

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import javax.inject.Inject

class LandingViewModel @Inject constructor(private val repository: LandingRepository): ViewModel() {
    val cachedCities  = this.repository.getCitiesFromDB().switchMap { cachedCities ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            if (cachedCities.isEmpty()) {
                Log.d("LandingViewModel", "loading from file")
                try {
                    val savedCities = this@LandingViewModel.repository.parseAndSaveToDB()
                    emit(Result.success(savedCities))
                } catch (exception: IOException) {
                    emit(Result.failure(exception))
                }
            } else {
                Log.d("LandingViewModel", "loading from db")
                emit(Result.success(cachedCities))
            }
        }
    }
}