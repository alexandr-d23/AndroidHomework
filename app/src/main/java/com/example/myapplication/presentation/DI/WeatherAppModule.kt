package com.example.myapplication.presentation.DI

import android.content.Context
import com.example.myapplication.data.repositories.LocationRepository
import com.example.myapplication.data.repositories.LocationRepositoryImpl
import com.example.myapplication.data.repositories.WeatherRepository
import com.example.myapplication.data.repositories.WeatherRepositoryImpl
import com.example.myapplication.data.retrofit.ApiFactory
import com.example.myapplication.data.retrofit.WeatherApi
import com.example.myapplication.data.room.WeatherDAO
import com.example.myapplication.data.room.WeatherDatabase
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class WeatherAppModule {

    @Singleton
    @Provides
    @Named("weatherApi")
    fun provideWeatherApi(): WeatherApi = ApiFactory.weatherApi

    @Singleton
    @Provides
    @Named("weatherDao")
    fun provideWeatherDao(@ApplicationContext context: Context): WeatherDAO =
        WeatherDatabase.getInstance(context).weatherDAO

    @Singleton
    @Provides
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context,
    ): FusedLocationProviderClient = FusedLocationProviderClient(context)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule{
    @Singleton
    @Binds
    abstract fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl) : WeatherRepository

    @Singleton
    @Binds
    abstract fun provideLocationRepository(locationRepositoryImpl: LocationRepositoryImpl): LocationRepository
}
