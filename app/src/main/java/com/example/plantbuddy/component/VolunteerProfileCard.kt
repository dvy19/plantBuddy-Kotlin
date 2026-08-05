package com.example.plantbuddy.component

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantbuddy.NGO.Screens.MeadowGreen
import com.example.plantbuddy.NGO.Screens.SageOutline
import com.example.plantbuddy.NGO.Screens.SoftLeafGreen
import com.example.plantbuddy.NGO.Screens.ForestGreen

// For loading network images (add Coil dependency to build.gradle if using Coil):
// import coil.compose.AsyncImage

data class VolunteerProfile(
    val name: String,
    val city: String,
    val gender: String,
    val phone: String,
    val imageUrl: String? = null
)

@Composable
fun VolunteerProfileCard(
    profile: VolunteerProfile,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, SageOutline),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header Row: Avatar, Name, Gender Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Profile Avatar Container
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(SoftLeafGreen)
                        .border(2.dp, MeadowGreen, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    /*
                    If using Coil for image loading:
                    AsyncImage(
                        model = profile.imageUrl,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    */
                    // Default Fallback Icon
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Placeholder",
                        tint = MeadowGreen,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Name & Gender Pill Badge
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = profile.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = ForestGreen,
                            fontSize = 20.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // Gender & Volunteer Badge Row
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = SoftLeafGreen,
                            shape = RoundedCornerShape(50),
                            border = BorderStroke(0.5.dp, SageOutline)
                        ) {
                            Text(
                                text = profile.gender,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = MeadowGreen,
                                    fontWeight = FontWeight.SemiBold
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }

                        // Subtle Volunteer Tag
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(ForestGreen, shape = RoundedCornerShape(50))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolunteerActivism,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Volunteer",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = SoftLeafGreen, thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            // Details Section: Location & Phone Number
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ProfileInfoRow(
                    icon = Icons.Default.LocationOn,
                    label = "City",
                    value = profile.city
                )

                ProfileInfoRow(
                    icon = Icons.Default.Call,
                    label = "Phone",
                    value = profile.phone
                )
            }
        }
    }
}

@Composable
private fun ProfileInfoRow(
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(SoftLeafGreen),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MeadowGreen,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MeadowGreen.copy(alpha = 0.8f)
                )
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = ForestGreen,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

// Preview Setup
@Preview(showBackground = true)
@Composable
fun VolunteerProfileCardPreview() {
    val sampleProfile = VolunteerProfile(
        name = "Aarav Sharma",
        city = "New Delhi, India",
        gender = "Male",
        phone = "+91 98765 43210"
    )

    Box(
        modifier = Modifier
            .background(Color(0xFFF7FAFL))
            .padding(8.dp)
    ) {
        VolunteerProfileCard(profile = sampleProfile)
    }
}