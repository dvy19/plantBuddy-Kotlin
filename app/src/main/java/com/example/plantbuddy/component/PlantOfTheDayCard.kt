package com.example.plantbuddy.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material.icons.outlined.Opacity
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantbuddy.plants.PlantViewModel

// --- Plant App Color Palette ---
private val ForestGreen = Color(0xFF1E3A27)
private val MeadowGreen = Color(0xFF2E6F40)
private val SoftLeafGreen = Color(0xFFE8F2EA)
private val SageOutline = Color(0xFFA8C3AD)

@Composable
fun PlantOfTheDayCard(
    name:String,

    scientific_name:String,
    category:String,
    why_today:String,
    care_tip:String,
    fun_fact:String,
    watering:String,
    sunlight:String,
    difficulty:String,
    pet_friendly:Boolean,
    air_purifying:Boolean,
    modifier: Modifier = Modifier
) {
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
                .padding(20.dp)
        ) {
            // Header Tag Row (Badge + Bookmark/Favorite)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MeadowGreen,
                    contentColor = Color.White
                ) {
                    Text(
                        text = "PLANT OF THE DAY",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color.White.copy(alpha = 0.7f), CircleShape)
                        .border(0.5.dp, SageOutline, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Save plant",
                        tint = ForestGreen,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Featured Image Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(ForestGreen.copy(alpha = 0.1f))
                    .border(1.dp, SageOutline.copy(alpha = 0.5f), RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {

                    // Placeholder visual when image is loading/null
                    Icon(
                        imageVector = Icons.Outlined.LocalFlorist,
                        contentDescription = null,
                        tint = MeadowGreen,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Plant Titles
            Text(
                text = name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = ForestGreen
            )

            Text(
                text = scientific_name,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MeadowGreen.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Quick Info Chips/Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PlantCareBadge(
                    icon = Icons.Outlined.LocalFlorist,
                    label = category,
                    modifier = Modifier.weight(1f)
                )
                PlantCareBadge(
                    icon = Icons.Outlined.Opacity,
                    label = watering,
                    modifier = Modifier.weight(1f)
                )
                PlantCareBadge(
                    icon = Icons.Outlined.LightMode,
                    label = sunlight,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }


@Composable
private fun PlantCareBadge(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color.White.copy(alpha = 0.7f),
        border = androidx.compose.foundation.BorderStroke(0.5.dp, SageOutline.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MeadowGreen,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                color = ForestGreen,
                maxLines = 1
            )
        }
    }
}
