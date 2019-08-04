package kishimotovn.pocketworksWeather.features.search

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kishimotovn.pocketworksWeather.data.local.models.PWCity
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val repository: SearchRepository): ViewModel() {
    val currentResults = MediatorLiveData<Pair<String, List<PWCity>>>()
    val resultMessage = MutableLiveData<String>()

    fun searchCity(term: String) {
        this.resultMessage.value = "Validating City ..."
        this.currentResults.value = Pair(term, listOf())

        if (term.isEmpty()) {
            this.currentResults.value = Pair("", listOf())
            this.resultMessage.value = ""
            return
        }

        this.currentResults.addSource(this.repository.getCities(term)) { result ->
            if (result.first == term) {
                this.resultMessage.value = ""
                this.currentResults.value = Pair(term, result.second)
            }
        }
    }
}