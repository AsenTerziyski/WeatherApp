package com.example.weatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weather_table")
class WeatherEntity (
    @PrimaryKey val cityName: String,
    val temperature: Double,
    val windSpeed: Double,
    val weatherCode: Int
)