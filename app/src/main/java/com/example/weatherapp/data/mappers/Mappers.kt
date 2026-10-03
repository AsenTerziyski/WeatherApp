package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.local.entity.WeatherEntity
import com.example.weatherapp.data.remote.dto.WeatherResponseDto
import com.example.weatherapp.domain.WeatherInfo

fun WeatherResponseDto.toEntity(cityName: String) = WeatherEntity(
    cityName = cityName,
    temperature = current.temperature2m,
    windSpeed = current.windSpeed10m,
    weatherCode = current.weatherCode
)

fun WeatherEntity.toDomain() = WeatherInfo(
    cityName = cityName,
    temperature = temperature,
    windSpeed = windSpeed,
    weatherCode = weatherCode
)