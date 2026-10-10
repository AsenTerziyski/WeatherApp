package com.example.weatherapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherapp.domain.WeatherInfo
import com.example.weatherapp.ui.WeatherUiState
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun WeatherHomeScreen(
    viewModel: WeatherViewModel,
    onRequestLocation: () -> Unit,
    onNavigateToDetails: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val eventFlow = viewModel.eventFlow
    val snackBarHostState = remember { SnackbarHostState() }


    LaunchedEffect(Unit) {
        eventFlow.collect { message ->
            snackBarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(
                    16.dp
                )
            ) {

                when (val state = uiState) {

                    WeatherUiState.Initial -> InitialScreen {
                        onRequestLocation.invoke()
                    }

                    WeatherUiState.Loading -> LoadingScreen()

                    is WeatherUiState.Success -> SuccessScreen(state) {

                    }

                    is WeatherUiState.Error -> ErrorScreen {
                        onRequestLocation.invoke()
                    }
                }
            }
        }
    }
}

@Composable
fun SuccessScreen(
    state: WeatherUiState.Success,
    onNavigateToDetails: () -> Unit
) {

    val weather = state.weatherInfo



}


@Preview
@Composable
fun SuccessScreenPreview() {
    WeatherAppTheme {
        SuccessScreen(
            state = WeatherUiState.Success(
                WeatherInfo(
                    cityName = "Sofia",
                    temperature = 25.0,
                    windSpeed = 5.0,
                    weatherCode = 1
                ),
            ),
            onNavigateToDetails = {}
        )
    }
}

@Composable
fun ErrorScreen(
    onRequestLocation: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Unexpected error occurred. Please try again!",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRequestLocation) {
            Text("Retry")
        }
    }
}

@Preview
@Composable
private fun ErrorScreenPreview() {
    WeatherAppTheme {
        ErrorScreen { }
    }
}

@Composable
private fun LoadingScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text("Fetching your location & weather...")
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingScreenPreview() {
    WeatherAppTheme {
        LoadingScreen()
    }
}

@Composable
private fun InitialScreen(onRequestLocation: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Welcome! Check weather at your location.",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRequestLocation) {
            Text("Get Weather at My Place")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InitialScreenPreview() {
    WeatherAppTheme {
        InitialScreen { }
    }
}