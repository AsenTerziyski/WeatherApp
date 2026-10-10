package com.example.weatherapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.LocationTracker
import com.example.weatherapp.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val locationTracker: LocationTracker
) : ViewModel() {

    private val _uiState: MutableStateFlow<WeatherUiState> =
        MutableStateFlow(WeatherUiState.Initial)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _eventChannel = Channel<String>()
    val eventFlow = _eventChannel.receiveAsFlow()

    fun fetchWeatherForCurrentLocation() {
        _uiState.update { WeatherUiState.Loading }
        viewModelScope.launch {
            try {
                val location = locationTracker.getCurrentLocation()
                if (location != null) {
                    val weather = getWeatherUseCase(
                        location.latitude,
                        location.longitude,
                        "My Current Location"
                    )
                    _uiState.value = WeatherUiState.Success(weather)
                } else {
                    fetchFallbackWeather("Could not retrieve GPS location. Using Sofia fallback.")
                }
            } catch (e: Exception) {
                fetchFallbackWeather("Location error: ${e.localizedMessage}. Using fallback.")
            }
        }
    }

    private suspend fun fetchFallbackWeather(errorMessage: String) {
        try {
            val fallbackWeatherInfo = getWeatherUseCase(42.69, 23.32, "Sofia")
            _uiState.update { WeatherUiState.Success(fallbackWeatherInfo) }
            _eventChannel.send(errorMessage)
        } catch (fallbackErrorMessage: Exception) {
            _uiState.update {
                WeatherUiState.Error(
                    fallbackErrorMessage.message ?: "Unknown error"
                )
            }
            _eventChannel.send(fallbackErrorMessage.message ?: "Unknown error")
        }
    }
}