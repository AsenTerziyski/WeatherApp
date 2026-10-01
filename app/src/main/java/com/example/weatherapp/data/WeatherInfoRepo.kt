package com.example.weatherapp.data

interface WeatherInfoRepo {
    suspend fun getWeather(
        lat: Double, lon: Double, cityName: String
    ): WeatherInfo
}