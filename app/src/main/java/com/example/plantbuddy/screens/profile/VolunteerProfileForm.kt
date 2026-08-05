package com.example.plantbuddy.screens.profile


import android.net.Uri
import android.se.omapi.Session
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Business
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.plantbuddy.NGO.createImagePart
import com.example.plantbuddy.auth.SessionManager
import com.example.plantbuddy.userDetails.CreateVolunteerProfileState
import com.example.plantbuddy.userDetails.DetailViewModel
import com.example.plantbuddy.userDetails.UserDetailRepo
import com.example.plantbuddy.userDetails.VolunteerProfileReq

// --- Plant App Color Palette ---
private val ForestGreen = Color(0xFF1E3A27)
private val MeadowGreen = Color(0xFF2E6F40)
private val SoftLeafGreen = Color(0xFFE8F2EA)
private val SageOutline = Color(0xFFA8C3AD)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolunteerProfileForm(
    mainNavController: NavController,
    onBackClick: () -> Unit = {},
) {
    // Form State
    var name by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }


    val scrollState = rememberScrollState()

    val context=LocalContext.current


    val sessionManager= SessionManager(context)

    val repo= UserDetailRepo(sessionManager)

    val viewModel: DetailViewModel=viewModel{
        DetailViewModel(repo)

    }


    val volunteerCreateState by viewModel.createVolunteerProfileState.collectAsState()



    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia()
        ) { uri ->

            selectedImageUri = uri

        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Volunteer Profile",
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
                text = "Create Your Volunteer Profile to make join request to NGOs.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Logo Selection Picker
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(SoftLeafGreen)
                    .border(2.dp, SageOutline, CircleShape),
                contentAlignment = Alignment.Center
            ) {

                if (selectedImageUri != null) {

                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = "Selected Logo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                } else {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Icon(
                            imageVector = Icons.Rounded.AddAPhoto,
                            contentDescription = "Upload Logo",
                            tint = MeadowGreen,
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    launcher.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                }
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Upload Logo",
                            style = MaterialTheme.typography.labelSmall,
                            color = MeadowGreen,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Form Inputs Section
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                BotanicalTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Name",
                    icon = Icons.Rounded.Business,
                    imeAction = ImeAction.Next
                )


                BotanicalTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = "Description / Mission",
                    icon = Icons.Rounded.Description,
                    singleLine = false,
                    maxLines = 4,
                    imeAction = ImeAction.Next
                )




                BotanicalTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = "City",
                    icon = Icons.Rounded.LocationCity,
                    imeAction = ImeAction.Next
                )

                BotanicalTextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    label = "Phone Number",
                    icon = Icons.Rounded.Phone,
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )


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
                    viewModel.createVolunteerProfile(
                        name,
                        phoneNumber,
                        city,
                        gender,
                        imagePart
                    )

                },
                enabled = name.isNotBlank() && city.isNotBlank() && phoneNumber.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MeadowGreen,
                    contentColor = Color.Black,
                    disabledContainerColor = SageOutline.copy(alpha = 0.5f)
                )
            ) {
                when(volunteerCreateState){
                    is CreateVolunteerProfileState.Idle->{
                        Text(text = "Submit")

                    }

                    is CreateVolunteerProfileState.Loading->{
                        Text(text = "Loading")

                    }

                    is CreateVolunteerProfileState.Success->{
                        //mainNavController.navigate("home")

                        Toast.makeText(context,"Profile Created Successfully",Toast.LENGTH_SHORT).show()
                    }

                    is CreateVolunteerProfileState.Error->{

                        Toast.makeText(context,"Error ",Toast.LENGTH_SHORT).show()
                    }
                    else -> {}

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
    singleLine: Boolean = true,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, fontSize = 14.sp) },
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
            unfocusedLabelColor = Color.Gray,

            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        modifier = Modifier.fillMaxWidth()
    )
}
