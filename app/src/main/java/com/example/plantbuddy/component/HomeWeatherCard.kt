package com.example.plantbuddy.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.Grain
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
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantbuddy.NGO.Screens.MeadowGreen
import com.example.plantbuddy.NGO.Screens.SageOutline
import com.example.plantbuddy.NGO.Screens.SoftLeafGreen
import com.example.plantbuddy.NGO.Screens.ForestGreen
import com.example.plantbuddy.weather.WeatherState
import com.example.plantbuddy.weather.WeatherViewModel
import okhttp3.internal.notify


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
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SoftLeafGreen
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, SageOutline),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    // Header Location Tag
                    Text(
                        text =weatherData.wind.speed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MeadowGreen,
                        letterSpacing = 1.2.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Main Weather Header Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = weatherData.main.temp,
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Bold,
                                color = ForestGreen
                            )
                            Text(
                                text = weatherData.name,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Medium,
                                color = MeadowGreen
                            )
                        }

                        // Weather Icon Container
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .background(ForestGreen, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.WbSunny,
                                contentDescription = "Weather condition icon",
                                tint = SoftLeafGreen,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Subtle Divider
                    HorizontalDivider(
                        color = SageOutline.copy(alpha = 0.5f),
                        thickness = 1.dp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Weather Details Grid (2 Rows)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        WeatherDetailItem(
                            icon = Icons.Outlined.WaterDrop,
                            label = "Humidity",
                            value = weatherData.main.humidity.toString(),
                            modifier = Modifier.weight(1f)
                        )

                        WeatherDetailItem(
                            icon = Icons.Outlined.Grain,
                            label = "Rainfall",
                            value = weatherData.rain.toString(),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        WeatherDetailItem(
                            icon = Icons.Outlined.WbSunny,
                            label = "Sunrise",
                            value = weatherData.sys.sunrise.toString(),
                            modifier = Modifier.weight(1f)
                        )

                        // Optional 4th metric placeholder or empty space for balance
                        WeatherDetailItem(
                            icon = Icons.Outlined.WaterDrop,
                            label = "Soil Moisture",
                            value = "Optimal (75%)",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }


                }




        is WeatherState.Error -> {

        }

    }
}

@Composable
private fun WeatherDetailItem(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Metric Icon Container
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(Color.White.copy(alpha = 0.7f), shape = RoundedCornerShape(10.dp))
                .border(0.5.dp, SageOutline.copy(alpha = 0.6f), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MeadowGreen,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Text Content
        Column {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = MeadowGreen.copy(alpha = 0.8f)
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = ForestGreen
            )
        }
    }
}