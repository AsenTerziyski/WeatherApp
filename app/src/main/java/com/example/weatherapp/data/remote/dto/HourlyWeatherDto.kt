package com.example.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class HourlyWeatherDto(
    @SerializedName("time")
    val time: List<String> = emptyList(),
    @SerializedName("temperature_2m")
    val temperatures: List<Double> = emptyList(),
    @SerializedName("relativehumidity_2m")
    val humidities: List<Double> = emptyList(),
    @SerializedName("windspeed_10m")
    val windSpeeds: List<Double> = emptyList()
)
