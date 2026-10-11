package com.example.weatherapp.domain

data class WeatherInfo(
    val cityName: String,
    val temperature: Double,
    val windSpeed: Double,
    val weatherCode: Int,
    val hourlyForecast: List<HourlyWeatherInfo> = emptyList(),
    val dailyForecast: List<DailyWeatherInfo> = emptyList()
)