package com.example.weatherapp.di

import com.example.weatherapp.data.WeatherInfoRepoImpl
import com.example.weatherapp.domain.WeatherInfoRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        impl: WeatherInfoRepoImpl
    ): WeatherInfoRepo

}