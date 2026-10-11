package com.example.weatherapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DailyWeatherDto(
    @SerializedName("time")
    val time: List<String> = emptyList(),
    @SerializedName("weathercode")
    val code: List<Int> = emptyList(),
    @SerializedName("temperature_2m_max")
    val temperatureMax: List<Double> = emptyList(),
    @SerializedName("temperature_2m_min")
    val temperatureMin: List<Double> = emptyList()
)
