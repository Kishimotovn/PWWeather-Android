package kishimotovn.pocketworksWeather.injection

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import kishimotovn.pocketworksWeather.Application
import kishimotovn.pocketworksWeather.utils.PreferencesManager
import dagger.Module
import dagger.Provides
import kishimotovn.pocketworksWeather.data.local.DAOs.PWCityDao
import kishimotovn.pocketworksWeather.data.local.PWDatabase
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {

    @Provides
    @Singleton
    fun provideApplication() = app

    @Provides
    @Singleton
    fun providePreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    fun providePreferencesManager(sharedPreferences: SharedPreferences): PreferencesManager {
        return PreferencesManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun providePWDatabase(application : Application): PWDatabase {
        return Room.databaseBuilder(application.applicationContext, PWDatabase::class.java, "pocketworks_weather.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun providePWCityDao(database: PWDatabase): PWCityDao {
        return database.cityDao()
    }
}