package com.example.plantbuddy.NGO.campaign

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantbuddy.auth.SessionManager

// --- Plant App Color Palette ---
private val ForestGreen = Color(0xFF1E3A27)
private val MeadowGreen = Color(0xFF2E6F40)
private val SoftLeafGreen = Color(0xFFE8F2EA)
private val SageOutline = Color(0xFFA8C3AD)
private val OngoingBadgeBg = Color(0xFFE2F4E5)
private val OngoingBadgeText = Color(0xFF1B6A31)

@Composable
fun OngoingCampaignCard(
    onCardClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    val context= LocalContext.current
    val repo= CampaignRepo(sessionManager = SessionManager(context))

    val viewModel: CampaignVM=viewModel{
        CampaignVM(repo)
    }

    val getActiveCampaignState by viewModel.getActiveCampaignState.collectAsState()

    LaunchedEffect(Unit)
    {

        viewModel.getActiveCampaign(true)
    }


    when(getActiveCampaignState){

        is GetActiveCampaignState.Idle -> {
            Text("Idle")

        }
        is GetActiveCampaignState.Loading -> {
            Text("Loading")

        }
        is GetActiveCampaignState.Success -> {

            val data = (getActiveCampaignState as GetActiveCampaignState.Success).data
                    .firstOrNull()

            data?.let {
                Card(
                    onClick = onCardClick,
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    ),
                    modifier = modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Top Row: Category/Organizer & Ongoing Tag
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text =data.title,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    letterSpacing = 1.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MeadowGreen
                                )
                            )

                            // Ongoing Status Tag
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(50))
                                    .background(OngoingBadgeBg)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    // Pulsing dot indicator
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(RoundedCornerShape(50))
                                            .background(OngoingBadgeText)
                                    )
                                    Text(
                                        text = "ONGOING",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            color = OngoingBadgeText,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Campaign Title
                        Text(
                            text = data.start_date,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = ForestGreen,
                                fontSize = 18.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Meta Details: Location & Dates
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            // Location Row
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.LocationOn,
                                    contentDescription = "Location",
                                    tint = MeadowGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = data.location,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.DarkGray,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            // Dates Row (Start Date - End Date)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.CalendarMonth,
                                    contentDescription = "Dates",
                                    tint = MeadowGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "${data.start_date}  •  ${data.end_date}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.DarkGray
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Progress Bar (Raised Funds / Volunteer Progress)
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Raised $${data.current_amount}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = ForestGreen
                                    )
                                )
                                Text(
                                    text = "Goal $${data.goal_amount}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Gray
                                )
                            }

                            val progress =
                                (data.current_amount.toDouble() / data.goal_amount.toDouble()).toFloat().coerceIn(0f, 1f)

                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(50)),
                                color = MeadowGreen,
                                trackColor = SoftLeafGreen
                            )
                        }
                    }
                }


            }


        }
        is GetActiveCampaignState.Error -> {
            Text("Failed to load Card ")
            Text((getActiveCampaignState as GetActiveCampaignState.Error).message)
            Log.d("Error", (getActiveCampaignState as GetActiveCampaignState.Error).message)
        }
    }

}

