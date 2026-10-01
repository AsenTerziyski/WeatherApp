package com.example.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

class WeatherResponseDto(
    @SerializedName("current")
    val current: CurrentWeatherDto
)