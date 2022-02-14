package cainwong.vegan.di

import android.app.Application
import androidx.room.Room
import cainwong.vegan.BuildConfig
import cainwong.vegan.api.PlacesApi
import cainwong.vegan.data.LocationRepository
import cainwong.vegan.data.LocationRepositoryImpl
import cainwong.vegan.data.PlacesDatabase
import cainwong.vegan.data.PlacesRepository
import cainwong.vegan.data.PlacesRepositoryImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder().addInterceptor(logger).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(PlacesApi.PLACES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providePlacesApi(retrofit: Retrofit): PlacesApi =
        retrofit.create(PlacesApi::class.java)

    @Provides
    @Singleton
    fun providePlacesDatabase(app: Application): PlacesDatabase =
        Room.databaseBuilder(app, PlacesDatabase::class.java, "places_database")
            .build()

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(app)

    @Provides
    @Singleton
    fun provideLocationRepository(
        app: Application,
        locationProvider: FusedLocationProviderClient,
    ): LocationRepository = LocationRepositoryImpl(app, locationProvider)

    @Provides
    @Singleton
    fun providePlacesRepository(
        api: PlacesApi,
        db: PlacesDatabase,
    ): PlacesRepository = PlacesRepositoryImpl(api, db)
}
