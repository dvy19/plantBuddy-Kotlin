package com.example.plantbuddy.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Professional gradient definition
val WeatherCardGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF2C3E50), // Dark Slate
        Color(0xFF3498DB)  // Sky Blue
    )
)

@Composable
fun HomeWeatherCard(
    weather: WeatherInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                // Top Row: Location & Condition text
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = weather.locationName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        )
                        Text(
                            text = weather.condition,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        )
                    }

                    // Temperature badge / High & Low
                    Text(
                        text = "H: ${weather.highTemp}°  L: ${weather.lowTemp}°",
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
                        text = "${weather.currentTemp}°",
                        fontSize = 56.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        lineHeight = 56.sp
                    )

                    // Optional: Weather Icon (Replace Icon with AsyncImage if loading from URL)
                    Icon(
                        imageVector = Icons.Rounded.WaterDrop, // Placeholder for Weather Condition Icon
                        contentDescription = weather.condition,
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
                        label = "${weather.humidity}%"
                    )
                    WeatherDetailChip(
                        icon = Icons.Rounded.Air,
                        label = "${weather.windSpeedKmH} km/h"
                    )
                }
            }
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