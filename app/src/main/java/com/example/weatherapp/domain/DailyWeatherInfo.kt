package com.example.weatherapp.domain

data class DailyWeatherInfo(
    val date: String,
    val maxTemperature: Double,
    val minTemperature: Double,
    val weatherCode: Int
)
