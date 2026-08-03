import androidx.compose.foundation.Image
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

/**
 * 2-Column Single Row Layout for Home Screen
 */
@Composable
fun HomeRowCard(
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
        CompactWeatherCard(
            temperature = temperature,
            condition = weatherCondition,
            humidity = humidity,
            rainfall = rainfall,
            sunrise = sunrise,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )

        // Right Column: Compact Plant Card
        CompactPlantCard(
            name = plantName,
            type = plantType,
            imageResId = plantImageResId,
            onClick = onPlantClick,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )
    }
}

@Composable
private fun CompactWeatherCard(
    temperature: String,
    condition: String,
    humidity: String,
    rainfall: String,
    sunrise: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = SoftLeafGreen),
        border = androidx.compose.foundation.BorderStroke(1.dp, SageOutline)
    ) {
        Column(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "WEATHER",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MeadowGreen,
                    letterSpacing = 1.sp
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(ForestGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.WbSunny,
                        contentDescription = null,
                        tint = SoftLeafGreen,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Temperature & Condition
            Text(
                text = temperature,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = ForestGreen
            )
            Text(
                text = condition,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = MeadowGreen,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = SageOutline.copy(alpha = 0.4f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // Metrics List
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                CompactMetricRow(icon = Icons.Outlined.WaterDrop, value = humidity, label = "Humidity")
                CompactMetricRow(icon = Icons.Outlined.Grain, value = rainfall, label = "Rainfall")
                CompactMetricRow(icon = Icons.Outlined.WbSunny, value = sunrise, label = "Sunrise")
            }
        }
    }
}

@Composable
private fun CompactMetricRow(
    icon: ImageVector,
    value: String,
    label: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MeadowGreen,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = value,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = ForestGreen
        )
    }
}

@Composable
private fun CompactPlantCard(
    name: String,
    type: String,
    imageResId: Int?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = SoftLeafGreen),
        border = androidx.compose.foundation.BorderStroke(1.dp, SageOutline)
    ) {
        Column(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Tag
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MeadowGreen
            ) {
                Text(
                    text = "PLANT OF DAY",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Plant Image Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(95.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(ForestGreen.copy(alpha = 0.08f))
                    .border(0.5.dp, SageOutline, RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (imageResId != null) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.LocalFlorist,
                        contentDescription = null,
                        tint = MeadowGreen,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Plant Details
            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = ForestGreen,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = type,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = MeadowGreen.copy(alpha = 0.85f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
