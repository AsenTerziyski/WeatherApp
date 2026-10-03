package com.example.weatherapp.data

import com.example.weatherapp.data.local.WeatherDao
import com.example.weatherapp.data.mappers.toDomain
import com.example.weatherapp.data.mappers.toEntity
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.WeatherInfo
import com.example.weatherapp.domain.WeatherInfoRepo
import javax.inject.Inject

class WeatherInfoRepoImpl @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao
) : WeatherInfoRepo {
    override suspend fun getWeather(
        lat: Double,
        lon: Double,
        cityName: String
    ): WeatherInfo = try {
        val response = api.getWeather(lat, lon)
        val entity = response.toEntity(cityName)
        dao.insertWeather(entity)
        entity.toDomain()
    } catch (e: Exception) {
        val cachedWeather = dao.getWeather(cityName)
        cachedWeather?.toDomain() ?: throw e
    }
}