package com.example.plantbuddy.screens.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// Color Palette Definition
private val ForestGreen = Color(0xFF1E3A27)
private val MeadowGreen = Color(0xFF2E6F40)
private val SoftLeafGreen = Color(0xFFE8F2EA)
private val SageOutline = Color(0xFFA8C3AD)

@Composable
fun NgoInfoCard(
    logo: String,
    name: String,
    city: String,
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
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo Image
            AsyncImage(
                model = logo,
                contentDescription = null ,
                modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(2.dp, MeadowGreen, CircleShape)
                        .background(Color.White)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // NGO Name
            Text(
                text = name,
                color = ForestGreen,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            // City Location
            Text(
                text = city,
                color = MeadowGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }}

