package kishimotovn.pocketworksWeather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import kishimotovn.pocketworksWeather.data.local.DAOs.PWCityDao
import kishimotovn.pocketworksWeather.data.local.models.PWCity

@Database(entities = [PWCity::class], version = 1, exportSchema = false)
abstract class PWDatabase: RoomDatabase() {
    abstract fun cityDao(): PWCityDao
}