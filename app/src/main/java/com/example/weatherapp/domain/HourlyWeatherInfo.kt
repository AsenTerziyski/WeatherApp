package com.example.weatherapp.domain

data class HourlyWeatherInfo(
    val time: String,
    val temperature: Double?,
    val weatherCode: Int
)
