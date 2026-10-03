package com.example.weatherapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<WeatherUiState> =
        MutableStateFlow(WeatherUiState.Initial)
    private val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _eventChannel = Channel<String>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun fetchWeather(
        lat: Double,
        lon: Double,
        cityName: String
    ) {
        viewModelScope.launch {
            _uiState.emit(WeatherUiState.Loading)
            try {
                val weatherInfo = getWeatherUseCase(lat, lon, cityName)
                _uiState.emit(WeatherUiState.Success(weatherInfo))
            } catch (e: Exception) {
                val errorMessage = e.message ?: "Unknown error"
                _uiState.emit(WeatherUiState.Error(errorMessage))
                _eventChannel.send(errorMessage)
            }
        }
    }
}