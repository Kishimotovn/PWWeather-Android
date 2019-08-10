package kishimotovn.pocketworksWeather.injection

import android.content.Context
import kishimotovn.pocketworksWeather.BuildConfig
import kishimotovn.pocketworksWeather.data.remote.DataService
import kishimotovn.pocketworksWeather.data.remote.RemoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import kishimotovn.pocketworksWeather.utils.DateFromTimeIntervalJSONAdapter
import java.util.*


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
                .addInterceptor { chain ->
                    val originalUrl = chain.request().url
                    val url = originalUrl.newBuilder()
                            .addQueryParameter("APPID", "18c80d238ec03ee72c5805687328da44")
                            .addQueryParameter("units", "metric")
                            .build()
                    val request = chain.request().newBuilder().url(url).build()
                    chain.proceed(request)
                }
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
                .add(Date::class.java, DateFromTimeIntervalJSONAdapter())
                .build()
        return Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl("http://api.openweathermap.org/")
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
    fun provideRepository(dataService: DataService): RemoteRepository {
        return RemoteRepository(dataService)
    }
}