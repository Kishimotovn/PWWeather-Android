package kishimotovn.pocketworksWeather.utils

import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import kishimotovn.pocketworksWeather.data.local.models.PWUnitSystem
import kishimotovn.pocketworksWeather.data.local.models.PWWindDirection
import java.text.DecimalFormat

class PWFormatter {
    companion object {
        fun windInfoString(direction: Double, speedInMetric: Double, unitSystem: PWUnitSystem): Spanned {
            var measure = speedInMetric
            var unit = "mph"
            when(unitSystem) {
                PWUnitSystem.imperial -> measure = measure * (3600.0/1609.344)
                PWUnitSystem.metric -> unit = "m/s"
            }

            val speedFormatter = DecimalFormat("#.##")
            val speedString = speedFormatter.format(measure)

            val directionString = PWWindDirection.from(direction).toString().toUpperCase()

            val winString = "$directionString $speedString $unit"
            val spannedString = SpannableString(winString)
            spannedString.setSpan(RelativeSizeSpan(0.7f), 0, directionString.length,0)
            spannedString.setSpan(StyleSpan(Typeface.BOLD), 0, directionString.length,0)
            return spannedString
        }
    }
}