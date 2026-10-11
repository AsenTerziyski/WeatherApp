package com.example.weatherapp.ui.views.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.WeatherUiState
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.views.WeatherDetailScreen
import com.example.weatherapp.ui.views.WeatherHomeScreen

@Composable
fun WeatherAppNavigation(viewModel: WeatherViewModel) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            WeatherHomeScreen(
                uiState = uiState,
                onRequestLocation = { viewModel.fetchWeatherForCurrentLocation() },
                onNavigateToDetails = { navController.navigate(Screen.Details) }
            )
        }

        composable<Screen.Details> {
            WeatherDetailScreen(
                weatherInfo = (uiState as? WeatherUiState.Success)?.weatherInfo,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}