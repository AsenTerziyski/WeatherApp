package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideWeatherDataBase(
        @ApplicationContext ctx: Context
    ): WeatherDatabase = Room
        .databaseBuilder(
            ctx,
            WeatherDatabase::class.java,
            "weather_db"
        ).build()

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDatabase: WeatherDatabase) = weatherDatabase.weatherDao()


}