package kishimotovn.pocketworksWeather.injection

import android.content.Context
import kishimotovn.pocketworksWeather.BuildConfig
import kishimotovn.pocketworksWeather.data.remote.DataService
import kishimotovn.pocketworksWeather.data.remote.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor


@Module
class NetworkModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://www.google.com/")
                .client(client)
                .build()
    }

    @Provides
    @Singleton
    fun provideDataService(retrofit: Retrofit): DataService {
        return retrofit.create(DataService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(dataService: DataService): Repository {
        return Repository(dataService)
    }
}