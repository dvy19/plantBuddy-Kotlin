package com.example.plantbuddy.NGO.campaign

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Campaign
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Group
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plantbuddy.NGO.createImagePart
import com.example.plantbuddy.auth.SessionManager

// --- Plant App Color Palette ---
private val ForestGreen = Color(0xFF1E3A27)
private val MeadowGreen = Color(0xFF2E6F40)
private val SoftLeafGreen = Color(0xFFE8F2EA)
private val SageOutline = Color(0xFFA8C3AD)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NgoCreateCampaign(
    mainNavController: NavController,
    onBackClick: () -> Unit = {},
) {
    // Form States
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var goalAmount by remember { mutableStateOf("") }
    var requiredVolunteers by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(true) }

    val context=LocalContext.current

    val repo=CampaignRepo(SessionManager(context))

    val viewModel: CampaignVM=viewModel(
        factory = CampaignVMFac(repo)
    )

    val createCampaignState by viewModel.state.collectAsState()


    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->

            selectedImageUri = uri

        }

    val scrollState = rememberScrollState()

    // Form Validation
    val isFormValid = title.isNotBlank() &&
            description.isNotBlank() &&
            location.isNotBlank() &&
            goalAmount.isNotBlank() &&
            requiredVolunteers.isNotBlank() &&
            startDate.isNotBlank() &&
            endDate.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Create Campaign",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = ForestGreen
                        )
                    )
                },
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
        containerColor = Color(0xFFF9FBF9)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Description
            Text(
                text = "Launch a new tree-planting drive, fundraiser, or community cleanup initiative.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Form Inputs
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Title
                BotanicalTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = "Campaign Title",
                    icon = Icons.Rounded.Campaign,
                    imeAction = ImeAction.Next
                )

                // Description
                BotanicalTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = "Description & Objectives",
                    icon = Icons.Rounded.Description,
                    singleLine = false,
                    maxLines = 4,
                    imeAction = ImeAction.Next
                )

                // Location
                BotanicalTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = "Location / Plantation Site",
                    icon = Icons.Rounded.LocationOn,
                    imeAction = ImeAction.Next
                )

                // Goal Amount & Volunteers (2-Column Row)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BotanicalTextField(
                        value = goalAmount,
                        onValueChange = { goalAmount = it },
                        label = "Goal Amount ($)",
                        icon = Icons.Rounded.MonetizationOn,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                        modifier = Modifier.weight(1f)
                    )

                    BotanicalTextField(
                        value = requiredVolunteers,
                        onValueChange = { requiredVolunteers = it },
                        label = "Volunteers Needed",
                        icon = Icons.Rounded.Group,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Start Date & End Date (2-Column Row)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BotanicalTextField(
                        value = startDate,
                        onValueChange = { startDate = it },
                        label = "Start Date",
                        icon = Icons.Rounded.CalendarMonth,
                        placeholder = "DD/MM/YYYY",
                        imeAction = ImeAction.Next,
                        modifier = Modifier.weight(1f)
                    )

                    BotanicalTextField(
                        value = endDate,
                        onValueChange = { endDate = it },
                        label = "End Date",
                        icon = Icons.Rounded.CalendarMonth,
                        placeholder = "DD/MM/YYYY",
                        imeAction = ImeAction.Done,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Active Status Toggle Box
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Publish Immediately",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ForestGreen
                                )
                            )
                            Text(
                                text = if (isActive) "Campaign will be publicly active" else "Campaign will be saved as draft",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }

                        Switch(
                            checked = isActive,
                            onCheckedChange = { isActive = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = MeadowGreen,
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = SoftLeafGreen
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Submit Button
            Button(
                onClick = {

                    val imagePart =
                        selectedImageUri?.let {

                            createImagePart(
                                context,
                                it
                            )

                        }

                   viewModel.createNgoCampaign(
                       title = title,
                       description = description,
                       location = location,
                       start_date = startDate,
                       end_date = endDate,
                       goal_amount = goalAmount.toDouble(),
                       required_volunteers = requiredVolunteers.toInt(),
                       is_active = isActive,
                       logo = imagePart

                   )
                },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MeadowGreen,
                    contentColor = Color.White,
                    disabledContainerColor = SageOutline.copy(alpha = 0.5f)
                )
            ) {
                when(createCampaignState){
                    is CreateCampaignState.Idle -> {
                        Text(
                            text = "Create Campaign",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ))
                    }
                    is CreateCampaignState.Success -> {

                        Text(
                            text = "Campaign Created",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )

                        )}

                    is CreateCampaignState.Loading -> {
                        Text(
                            text = "Loading",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold

                            ))}

                    is CreateCampaignState.Error -> {
                        Text(
                            text = "Error",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ))}



                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun BotanicalTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    placeholder: String = "",
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, fontSize = 13.sp) },
        placeholder = { if (placeholder.isNotEmpty()) Text(text = placeholder, fontSize = 13.sp, color = Color.LightGray) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MeadowGreen
            )
        },
        singleLine = singleLine,
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = MeadowGreen,
            unfocusedBorderColor = SageOutline,
            focusedLabelColor = MeadowGreen,
            unfocusedLabelColor = Color.Gray
        ),
        modifier = modifier.fillMaxWidth()
    )
}