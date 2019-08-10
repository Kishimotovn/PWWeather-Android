package kishimotovn.pocketworksWeather.data.local.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.*

@Parcelize
@Serializable
data class PWCoordinates(var lat: Double?,
                         var lon: Double?): Parcelable

@Parcelize
@Serializable
@Entity(tableName="cities")
data class PWCity(@PrimaryKey var id: String,
                 var name: String = "",
                 var country: String = "",
                 @Embedded var coord: PWCoordinates?): Parcelable