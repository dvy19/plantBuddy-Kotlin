package com.example.plantbuddy.screens.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.plantbuddy.NGO.campaign.Campaign
import com.example.plantbuddy.NGO.campaign.CampaignRepo
import com.example.plantbuddy.NGO.campaign.CampaignVM
import com.example.plantbuddy.NGO.campaign.GetSingleCampaignState
import com.example.plantbuddy.auth.SessionManager
import com.example.plantbuddy.screens.community.DetailRow
import com.example.plantbuddy.screens.community.MetricCard

// Color Palette Definition
private val ForestGreen = Color(0xFF1E3A27)
private val MeadowGreen = Color(0xFF2E6F40)
private val SoftLeafGreen = Color(0xFFE8F2EA)
private val SageOutline = Color(0xFFA8C3AD)
private val ActiveGreen = Color(0xFF2E7D32)
private val InactiveGray = Color(0xFF757575)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignDetailScreen(
    mainNavController: NavController,
    campaign_id:Int?,
    onBackClick: () -> Unit = {},
    onDonateClick: () -> Unit = {},
    onVolunteerClick: () -> Unit = {}
) {


    val context=LocalContext.current
    val scrollState = rememberScrollState()

    val repo= CampaignRepo(sessionManager = SessionManager(context))

    val viewModel:CampaignVM=viewModel{
        CampaignVM(repo)
    }

    LaunchedEffect(Unit) {
        campaign_id?.let{
            viewModel.getSingleCampaign(campaign_id)

        }
    }

    val getSingleCampaignState by viewModel.getSingleCampaignState.collectAsState()




    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Campaign Details",
                        color = ForestGreen,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    // Replace painter with painterResource(R.drawable.ic_back) as needed
                    IconButton(onClick = onBackClick) {
                        Text("←", fontSize = 24.sp, color = ForestGreen)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SoftLeafGreen
                )
            )
        },
        bottomBar = {
            // Action Buttons Bar
            SurfaceActionFooter(
                onDonateClick = onDonateClick,
                onVolunteerClick = onVolunteerClick,
                isActive = true
            )
        },
        containerColor = Color.White
    ) { innerPadding ->

        when(getSingleCampaignState){
            is GetSingleCampaignState.Idle -> {
                Text("Idle")
            }
            is GetSingleCampaignState.Loading -> {
                Text("Loading")

            }

            is GetSingleCampaignState.Success -> {
                val data = (getSingleCampaignState as GetSingleCampaignState.Success).data


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    // Header Section: Logo + Title + Status
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = data.logo,
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            // Active / Inactive Status Tag
                            val badgeColor = if (data.is_active) ActiveGreen else InactiveGray
                            Text(
                                text = if (data.is_active) "ACTIVE" else "INACTIVE",
                                color = badgeColor,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .background(
                                        color = badgeColor.copy(alpha = 0.12f),
                                        shape = RoundedCornerShape(50)
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = badgeColor.copy(alpha = 0.3f),
                                        shape = RoundedCornerShape(50)
                                    )
                                    .padding(horizontal = 10.dp, vertical = 3.dp)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = data.title,
                                color = ForestGreen,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleLarge
                            )


                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Metrics Cards Row (Goal Amount & Volunteers Required)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        MetricCard(
                            title = "Goal Amount",
                            value = data.goal_amount,
                            modifier = Modifier.weight(1f)
                        )
                        MetricCard(
                            title = "Volunteers Needed",
                            value = "${data.required_volunteers}",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Info Section Card (Dates & Location Details)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, SageOutline, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SoftLeafGreen)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            DetailRow(label = "Duration", value = "${data.start_date} – ${data.end_date}")
                            DetailRow(label = "City", value = data.location)
                            DetailRow(label = "Location", value = data.created_at)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Campaign Description
                    Text(
                        text = "About the Campaign",
                        color = ForestGreen,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = data.description,
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            is GetSingleCampaignState.Error -> {
                Text("Failed to load plants")
            }
        }

    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.border(1.dp, SageOutline, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SoftLeafGreen)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                color = MeadowGreen,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                color = ForestGreen,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column {
        Text(
            text = label,
            color = MeadowGreen,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            color = ForestGreen,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun SurfaceActionFooter(
    onDonateClick: () -> Unit,
    onVolunteerClick: () -> Unit,
    isActive: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(width = (0.5).dp, color = SageOutline)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onVolunteerClick,
                enabled = isActive,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, MeadowGreen)
            ) {
                Text("Volunteer", color = MeadowGreen, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = onDonateClick,
                enabled = isActive,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ForestGreen,
                    contentColor = Color.White
                )
            ) {
                Text("Donate", fontWeight = FontWeight.Bold)
            }
        }
    }
}