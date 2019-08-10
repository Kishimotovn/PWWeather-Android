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

        fun temperatureString(value: Double, unitSystem: PWUnitSystem): String {
            var measure = value
            var unit = "°F"

            when(unitSystem) {
                PWUnitSystem.imperial -> measure = measure*1.8 + 32
                PWUnitSystem.metric -> unit = "°C"
            }

            val formatter = DecimalFormat("#")
            val formattedMeasure = formatter.format(measure)

            return "$formattedMeasure $unit"
        }

        fun pressureString(value: Double): String {
            val formatter = DecimalFormat("#.###")
            val measure = formatter.format(value)

            return "$measure hPa"
        }

        fun lengthString(value: Double, unitSystem: PWUnitSystem): String {
            var measure = value
            var unit = "mi"
            when(unitSystem) {
                PWUnitSystem.imperial -> measure = (measure) / 1609.344
                PWUnitSystem.metric -> {
                    measure = measure / 1000
                    unit = "km"
                }
            }

            val formatter = DecimalFormat("#.#")
            val formattedMeasure = formatter.format(measure)

            return "$formattedMeasure $unit"
        }
    }
}