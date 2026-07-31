package com.example.plantbuddy.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// Custom palette constants
private val ForestGreen = Color(0xFF2E7D32)
private val LightGreenBg = Color(0xFFF1F8E9)
private val MintChip = Color(0xFFE8F5E9)

@Composable
fun PlantGridCard(
    plantName: String,
    plantType: String,
    imageUrl: String,
    isAdded: Boolean = false,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable{
                onNavigate()
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // 1. Plant Image Container with Action Button Overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // Square aspect ratio for grid balance
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightGreenBg)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = plantName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Small Action Button (Top Right Overlay)
                IconButton(
                    onClick = onAddClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            if (isAdded) ForestGreen else Color.White.copy(alpha = 0.9f)
                        )
                ) {
                    Icon(
                        imageVector = if (isAdded) Icons.Default.Check else Icons.Default.Add,
                        contentDescription = "Add to My Plants",
                        tint = if (isAdded) Color.White else ForestGreen,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 2. Plant Name
            Text(
                text = plantName,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20), // Dark green text
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // 3. Plant Type Badge / Subtitle
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = MintChip,
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(
                    text = plantType,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = ForestGreen,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}