package com.example.weatherapp.ui.views.navigation

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.WeatherUiState
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.views.WeatherDetailScreen
import com.example.weatherapp.ui.views.WeatherHomeScreen

@Composable
fun WeatherAppNavigation(viewModel: WeatherViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            viewModel.fetchWeatherForCurrentLocation()
        }
    }


    fun requestLocationOrPermission() {
        val fineGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (fineGranted || coarseGranted) {
            viewModel.fetchWeatherForCurrentLocation()
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            WeatherHomeScreen(
                uiState = uiState,
                onRequestLocation = { requestLocationOrPermission() },
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