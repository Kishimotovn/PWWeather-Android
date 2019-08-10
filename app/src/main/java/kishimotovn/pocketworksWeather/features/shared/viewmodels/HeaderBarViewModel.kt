package kishimotovn.pocketworksWeather.features.shared.viewmodels

import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class HeaderBarViewModel(title: String, leftButtonDrawable: Drawable?, inset: Int = 0): ViewModel() {
    val title = ObservableField<String>(title)
    val leftButtonDrawable = ObservableField<Drawable?>(leftButtonDrawable?.let{ InsetDrawable(leftButtonDrawable, inset) } ?: null)
}