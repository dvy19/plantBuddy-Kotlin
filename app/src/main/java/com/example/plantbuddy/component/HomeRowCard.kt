package com.example.plantbuddy.component
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Grain
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- Plant App Color Palette ---
private val ForestGreen = Color(0xFF1E3A27)
private val MeadowGreen = Color(0xFF2E6F40)
private val SoftLeafGreen = Color(0xFFE8F2EA)
private val SageOutline = Color(0xFFA8C3AD)
@Composable
fun HomeScreenCardRow(
    modifier: Modifier = Modifier,
    // Weather Data
    temperature: String = "24°C",
    weatherCondition: String = "Partly Cloudy",
    humidity: String = "68%",
    rainfall: String = "2.4 mm",
    sunrise: String = "06:12 AM",
    // Plant Data
    plantName: String = "Monstera",
    plantType: String = "Tropical / Indoor",
    plantImageResId: Int? = null,
    onPlantClick: () -> Unit = {}
) {


    // IntrinsicSize.Min matches the height of both cards equally
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Left Column: Compact Weather Card
        HomeWeatherCard(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            onClick = {}
        )


        // Right Column: Compact Plant Card
        PlantOfTheDayCard(
            plantName = plantName,
            plantType = plantType,
            imageResId = plantImageResId,
            onFavoriteClick = {},
            modifier = Modifier
        )
    }
}