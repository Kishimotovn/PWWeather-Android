package kishimotovn.pocketworksWeather.data.local.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.*

@Serializable
data class PWCoordinates(var lat: Double?,
                         var lon: Double?)

@Serializable
@Entity(tableName="cities")
data class PWCity(@PrimaryKey var id: String,
                 var name: String = "",
                 var country: String = "",
                 @Embedded var coord: PWCoordinates?)