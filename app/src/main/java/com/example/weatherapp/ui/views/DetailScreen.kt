package com.example.weatherapp.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherapp.domain.WeatherInfo
import com.example.weatherapp.ui.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailScreen(
    weatherInfo: WeatherInfo?,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(weatherInfo?.cityName ?: "Weather Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        DetailView(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            weatherInfo = weatherInfo
        )
    }
}

@Composable
private fun DetailView(modifier: Modifier, weatherInfo: WeatherInfo?) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        if (weatherInfo != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Location: ${weatherInfo.cityName}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    HorizontalDivider()
                    DetailRow(label = "Temperature", value = "${weatherInfo.temperature}°C")
                    DetailRow(label = "Wind Speed", value = "${weatherInfo.windSpeed} km/h")
                    DetailRow(label = "Weather Code", value = "${weatherInfo.weatherCode}")
                }
            }
        } else {
            Text(
                text = "No detailed weather data available.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
@Preview(showBackground = true)
private fun DetailRowPreview() {
    WeatherAppTheme {
        DetailRow(label = "Temperature", value = "25°C")
    }
}

@Composable
@Preview(showBackground = true)
private fun DetailViewPreview() {
    WeatherAppTheme {
        DetailView(modifier = Modifier, WeatherInfo(
            cityName = "Sofia",
            temperature = 25.0,
            windSpeed = 5.0,
            weatherCode = 1
        ))
    }
}