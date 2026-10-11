package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.local.entity.WeatherEntity
import com.example.weatherapp.data.remote.dto.DailyWeatherDto
import com.example.weatherapp.data.remote.dto.HourlyWeatherDto
import com.example.weatherapp.data.remote.dto.WeatherResponseDto
import com.example.weatherapp.domain.DailyWeatherInfo
import com.example.weatherapp.domain.HourlyWeatherInfo
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

fun HourlyWeatherDto.toDomain(): List<HourlyWeatherInfo> = time.indices.map { i ->
    HourlyWeatherInfo(
        time = time[i],
        temperature = temperatures.getOrElse(i) { 0.0 },
        weatherCode = 0
    )
}


fun DailyWeatherDto.toDomain(): List<DailyWeatherInfo> = time.indices.map { i ->
    DailyWeatherInfo(
        date = time[i],
        maxTemperature = temperatureMax.getOrElse(i) { 0.0 },
        minTemperature = temperatureMin.getOrElse(i) { 0.0 },
        weatherCode = code.getOrElse(i) { 0 }
    )
}


fun WeatherResponseDto.toDomain() = WeatherInfo(
    cityName = "",
    temperature = current.temperature2m,
    windSpeed = current.windSpeed10m,
    weatherCode = current.weatherCode,
    dailyForecast = daily?.toDomain() ?: emptyList(),
    hourlyForecast = hourly?.toDomain() ?: emptyList()
)
