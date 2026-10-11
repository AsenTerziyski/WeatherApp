package com.example.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("current")
    val current: CurrentWeatherDto,
    @SerializedName("daily")
    val daily: DailyWeatherDto? = null,
    @SerializedName("hourly")
    val hourly: HourlyWeatherDto? = null
)
