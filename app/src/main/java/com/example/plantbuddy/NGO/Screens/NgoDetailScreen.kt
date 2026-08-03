package com.example.plantbuddy.NGO.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Campaign
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plantbuddy.NGO.details.GetSingleNgoState
import com.example.plantbuddy.NGO.details.NgoDetailsRepo
import com.example.plantbuddy.NGO.details.NgoDetailsVM
import com.example.plantbuddy.auth.SessionManager

// --- Plant App Color Palette ---
val ForestGreen = Color(0xFF1E3A27)
val MeadowGreen = Color(0xFF2E6F40)
 val SoftLeafGreen = Color(0xFFE8F2EA)
val SageOutline = Color(0xFFA8C3AD)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NgoDetailScreen(
    mainNavController: NavController,
    ngoId:Int?,
    onBackClick: () -> Unit = {},
    onViewOngoingCampaignClick: () -> Unit = {},
    onJoinNowClick: () -> Unit = {},
    onDonateNowClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    val context= LocalContext.current

    val repo= NgoDetailsRepo(sessionManager = SessionManager(context))

    val viewModel: NgoDetailsVM=viewModel{
        NgoDetailsVM(repo)

    }

    LaunchedEffect(Unit) {
        ngoId?.let{
            viewModel.getSingleNgo(ngoId)
        }
    }

    val getSingleNgoState by viewModel.getSingleNgoState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back",
                            tint = ForestGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF9FBF9)
                )
            )
        },
        bottomBar = {
            // Sticky Bottom Bar for Action Buttons
            Surface(
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Join Now Button
                    // Donate Now Button
                    Button(
                        onClick = onDonateNowClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MeadowGreen,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Donate Now",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        },
        containerColor = Color(0xFFF9FBF9)
    ) { innerPadding ->

        when(getSingleNgoState){
            is GetSingleNgoState.Idle -> {
                Text("Idle")
            }
            is GetSingleNgoState.Loading -> {
                Text("Loading")
                }

            is GetSingleNgoState.Success -> {

                val data=(getSingleNgoState as GetSingleNgoState.Success).data

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .verticalScroll(scrollState)
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo Header
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(SoftLeafGreen)
                            .border(2.dp, SageOutline, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Business,
                            contentDescription = "${data.name} Logo",
                            tint = MeadowGreen,
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // NGO Name
                    Text(
                        text = data.name,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = ForestGreen
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // View Ongoing Campaigns Button
                    Card(
                        onClick = onViewOngoingCampaignClick,
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = SoftLeafGreen
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Campaign,
                                    contentDescription = null,
                                    tint = MeadowGreen,
                                    modifier = Modifier.size(24.dp)
                                        .clickable{
                                            mainNavController.navigate("ngo_all_active_campaigns/${data.id}")
                                        }
                                )
                                Text(
                                    text = "View Ongoing Campaigns",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = ForestGreen
                                    )
                                )
                            }

                            Icon(
                                imageVector = Icons.Rounded.ChevronRight,
                                contentDescription = "Go",
                                tint = MeadowGreen
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Description Section
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "About Organization",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = ForestGreen
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = data.description,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                lineHeight = 22.sp
                            ),
                            color = Color.DarkGray
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Contact Information Section
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Contact Information",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = ForestGreen
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                ContactInfoItem(
                                    icon = Icons.Rounded.LocationCity,
                                    title = "City",
                                    value = data.city
                                )

                                ContactInfoItem(
                                    icon = Icons.Rounded.LocationOn,
                                    title = "Address",
                                    value = data.address
                                )

                                ContactInfoItem(
                                    icon = Icons.Rounded.Call,
                                    title = "Phone",
                                    value = data.phone_number
                                )

                                ContactInfoItem(
                                    icon = Icons.Rounded.Language,
                                    title = "Website",
                                    value = data.website
                                )
                            }
                        }
                    }

                    // Extra padding at bottom to ensure content isn't covered by sticky bottom bar
                    Spacer(modifier = Modifier.height(32.dp))
                }

            }
            is GetSingleNgoState.Error -> {
                Text("Failed to load plants")
            }

        }
    }
}

@Composable
private fun ContactInfoItem(
    icon: ImageVector,
    title: String,
    value: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(SoftLeafGreen),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MeadowGreen,
                modifier = Modifier.size(20.dp)
            )
        }

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
            Text(
                text = value.ifBlank { "N/A" },
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = ForestGreen
                )
            )
        }
    }
}

