package kishimotovn.pocketworksWeather.features.search.viewmodels

import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import kishimotovn.pocketworksWeather.data.local.models.PWCity

class SearchResultItemViewModel(city: PWCity, searchTerm: String): ViewModel() {
    val cityName: ObservableField<SpannableString> = {
        val cityNameString =  SpannableString(city.name)
        val searchTermIndex = city.name.indexOf(searchTerm, ignoreCase = true)
        if (searchTermIndex != -1) {
            Log.d("AHA", "index: ${searchTermIndex}")
            cityNameString.setSpan(StyleSpan(Typeface.BOLD), searchTermIndex, searchTermIndex + searchTerm.length, 0)
        }
        ObservableField(cityNameString)
    }()
}