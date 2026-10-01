package com.example.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

class CurrentWeatherDto (
    @SerializedName("temperature_2m")
    val temperature2m: Double,
    @SerializedName("wind_speed_10m")
    val windSpeed10m: Double,
    @SerializedName("weather_code")
    val weatherCode: Int
)