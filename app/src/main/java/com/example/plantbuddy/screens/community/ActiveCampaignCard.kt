package com.example.plantbuddy.screens.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// Color Palette Definition
private val ForestGreen = Color(0xFF1E3A27)
private val MeadowGreen = Color(0xFF2E6F40)
private val SoftLeafGreen = Color(0xFFE8F2EA)
private val SageOutline = Color(0xFFA8C3AD)
private val ActiveGreen = Color(0xFF2E7D32) // Bright accent green for the active tag

@Composable
fun ActiveCampaignCard(
    logo: String,
    ngoTitle: String,
    city: String,
    startDate: String,
    endDate: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable{
                onClick()
            }
            .border(
                width = 1.dp,
                color = SageOutline,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = SoftLeafGreen
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Active Badge (Top Right)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // Active Highlight Tag
                Text(
                    text = "ACTIVE",
                    color = ActiveGreen,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .background(
                            color = ActiveGreen.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(50)
                        )
                        .border(
                            width = 1.dp,
                            color = ActiveGreen.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(50)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Body Row: Logo + Information Column
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // NGO Logo
                AsyncImage(
                    model = logo,
                    contentDescription = "NGO Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                // NGO Info & Campaign Details
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // NGO Title
                    Text(
                        text = ngoTitle,
                        color = ForestGreen,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    // City Location
                    Text(
                        text = city,
                        color = MeadowGreen,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Campaign Dates
                    Text(
                        text = "$startDate – $endDate",
                        color = ForestGreen.copy(alpha = 0.8f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}