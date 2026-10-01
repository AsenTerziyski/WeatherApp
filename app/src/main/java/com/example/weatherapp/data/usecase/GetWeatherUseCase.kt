package com.example.weatherapp.data.usecase

import com.example.weatherapp.data.WeatherInfo
import com.example.weatherapp.data.WeatherInfoRepo
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherInfoRepo: WeatherInfoRepo
) {
    suspend operator fun invoke(
        lat: Double, lon: Double, cityName: String
    ): WeatherInfo = weatherInfoRepo.getWeather(lat, lon, cityName)

}