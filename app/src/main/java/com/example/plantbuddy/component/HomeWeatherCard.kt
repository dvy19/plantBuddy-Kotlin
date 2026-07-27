package com.example.plantbuddy.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantbuddy.weather.WeatherState
import com.example.plantbuddy.weather.WeatherViewModel
import okhttp3.internal.notify

// Professional gradient definition
val WeatherCardGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF2C3E50), // Dark Slate
        Color(0xFF3498DB)  // Sky Blue
    )
)

@Composable
fun HomeWeatherCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val weatherViewModel: WeatherViewModel = viewModel()

    val weatherState by weatherViewModel.weatherState.collectAsState()

    LaunchedEffect(Unit) {
        weatherViewModel.fetchWeather("Delhi")
    }

    when(val state=weatherState){
        is WeatherState.Idle -> {
            Text("Idle")
        }
        is WeatherState.Loading -> {
            Text("Loading")
            }

        is WeatherState.Success -> {

            val weatherData=state.data

            Card(
                onClick = onClick,
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = modifier
                    .fillMaxWidth()
                    .height(200.dp) // Ideal fixed height for a homescreen widget-style card
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(WeatherCardGradient)
                        .padding(20.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Top Row: name and humidity
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column {
                                Text(
                                    text = weatherData.name,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White.copy(alpha = 0.9f)
                                    )
                                )

                                Text(
                                    text = "Sunrise: ${weatherData.sys.sunrise}%",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = Color.White.copy(alpha = 0.8f)
                                    )
                                )
                            }

                            // Temperature badge / High & Low
                            Text(
                                text = "H: ${weatherData.main.temp}°",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            )
                        }

                        // Middle Row: Main Temperature
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${weatherData.main.temp}°",
                                fontSize = 56.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                lineHeight = 56.sp
                            )

                            // Optional: Weather Icon (Replace Icon with AsyncImage if loading from URL)
                            Icon(
                                imageVector = Icons.Rounded.WaterDrop, // Placeholder for Weather Condition Icon
                                contentDescription = weatherData.name,
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                        }

                        // Bottom Row: Secondary Metrics (Humidity, Wind)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            WeatherDetailChip(
                                icon = Icons.Rounded.WaterDrop,
                                label = "${weatherData.main.humidity}%"
                            )
                            WeatherDetailChip(
                                icon = Icons.Rounded.Air,
                                label = "${weatherData.wind.speed} km/h"
                            )
                        }
                    }
                }
            }

        }

        is WeatherState.Error -> {

        }

    }
}

@Composable
private fun WeatherDetailChip(
    icon: ImageVector,
    label: String
) {
    Surface(
        color = Color.White.copy(alpha = 0.15f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}