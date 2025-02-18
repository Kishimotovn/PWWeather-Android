package kishimotovn.pocketworksWeather.data.local.DAOs

import androidx.lifecycle.LiveData
import androidx.room.*
import kishimotovn.pocketworksWeather.data.local.models.PWCity

@Dao
interface PWCityDao {
    @Query("SELECT * FROM cities")
    fun getCities(): LiveData<List<PWCity>>

    @Query("SELECT * FROM cities where name LIKE '%' || :searchTerm || '%' LIMIT 30")
    fun searchCity(searchTerm: String): LiveData<List<PWCity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCities(cities: List<PWCity>)

    @Query("DELETE FROM cities")
    fun deleteAll()

    @Update
    fun updateCity(city: PWCity)
}