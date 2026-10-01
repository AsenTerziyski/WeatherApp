package com.example.weatherapp.domain

interface WeatherInfoRepo {
    suspend fun getWeather(
        lat: Double, lon: Double, cityName: String
    ): WeatherInfo
}