package com.example.weatherapp.domain

data class WeatherInfo(
    val cityName: String,
    val temperature: Double,
    val windSpeed: Double,
    val weatherCode: Int
)