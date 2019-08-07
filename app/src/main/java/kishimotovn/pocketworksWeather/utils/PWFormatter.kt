package kishimotovn.pocketworksWeather.utils

import kishimotovn.pocketworksWeather.data.local.models.PWUnitSystem
import kishimotovn.pocketworksWeather.data.local.models.PWWindDirection
import java.text.DecimalFormat

class PWFormatter {
    companion object {
        fun windInfoString(direction: Double, speedInMetric: Double, unitSystem: PWUnitSystem): String {
            var measure = speedInMetric
            var unit = "mph"
            when(unitSystem) {
                PWUnitSystem.imperial -> measure = measure * (3600.0/1609.344)
                PWUnitSystem.metric -> unit = "m/s"
            }

            val speedFormatter = DecimalFormat("#.##")
            val speedString = speedFormatter.format(measure)

            val directionString = PWWindDirection.from(direction)

            return "$directionString $speedString $unit"
        }
    }
}