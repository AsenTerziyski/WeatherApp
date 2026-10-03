package com.example.weatherapp.ui

import com.example.weatherapp.domain.WeatherInfo

sealed interface WeatherUiState {
    object Initial : WeatherUiState
    object Loading : WeatherUiState
    data class Success(val weatherInfo: WeatherInfo) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}
