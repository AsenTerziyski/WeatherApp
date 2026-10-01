package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.local.entity.WeatherEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_table where cityName = :cityName")
    suspend fun getWeather(cityName: String): WeatherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherEntity: WeatherEntity)

}