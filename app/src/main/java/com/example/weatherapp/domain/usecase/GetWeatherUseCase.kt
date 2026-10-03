package com.example.weatherapp.domain.usecase

import com.example.weatherapp.domain.WeatherInfo
import com.example.weatherapp.domain.WeatherInfoRepo
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherInfoRepo: WeatherInfoRepo
) {
    suspend operator fun invoke(lat: Double, lon: Double, cityName: String): WeatherInfo =
        weatherInfoRepo.getWeather(lat, lon, cityName)

}