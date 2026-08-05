package com.example.plantbuddy.screens.profile

import android.se.omapi.Session
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plantbuddy.auth.AuthViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.plantbuddy.Screens
import com.example.plantbuddy.auth.RegisterState
import com.example.plantbuddy.auth.SessionManager
import com.example.plantbuddy.auth.SignupRequest
import com.example.plantbuddy.component.VolunteerProfileCard
import com.example.plantbuddy.screens.ForestGreen
import com.example.plantbuddy.screens.SoftWhiteBackground
import com.example.plantbuddy.userDetails.DetailViewModel
import com.example.plantbuddy.userDetails.GetProfileState
import com.example.plantbuddy.userDetails.GetProfileState.Idle
import com.example.plantbuddy.userDetails.UserDetailRepo

@Composable
fun UserProfileScreenLayout(
    mainNavController: NavController

) {
    val context=LocalContext.current
    val sessionManager= SessionManager(context)

    val accessToken=sessionManager.getAccessToken()

    val repo= UserDetailRepo(sessionManager)

    val viewModel: DetailViewModel=viewModel{
        DetailViewModel(repo)
    }

    val getProfileState by viewModel.getProfileState.collectAsState()

    Log.d("maccess token",accessToken.toString())
    if(accessToken==null){

        LandingScreen(
            onEnterClick = {
                mainNavController.navigate(Screens.LoginScreen.route)
            },
            onCreateAccountClick = {
                mainNavController.navigate(Screens.RegisterScreen.route)
            }

        )
    }
    else{

        LaunchedEffect(Unit) {
            Log.d("Compose", "Calling API")
            viewModel.getUserProfile()

        }

        Log.d("m",getProfileState.toString())

        when(getProfileState){


            is Idle -> {
                Log.d("m",getProfileState.toString())


            }
            is GetProfileState.Loading -> {
                Log.d("m",getProfileState.toString())

                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = ForestGreen
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Loading...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }
            is GetProfileState.Success -> {
                Log.d("GetProfileState.Success -> {",getProfileState.toString())

                val userdata=(getProfileState as GetProfileState.Success).data

                ProfileScreen(
                    userCity = userdata.city,
                    userName =userdata.name,
                    userEmail = "NA",

                    onLogoutClick = {
                        sessionManager.logout()
                    },
                    onYourPlantsClick = {
                        mainNavController.navigate(Screens.PlantCatalogScreen.route)
                    }
                )

            }

            is GetProfileState.Error -> {
                Log.d("m",getProfileState.toString())

                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = ForestGreen
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Error Loading Data",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }
            }

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userName: String,
    userEmail: String,
    userCity: String ,
    onYourPlantsClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    "Profile",
                    color=Color.Black

                                    ) },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SoftWhiteBackground
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Profile Avatar Placeholder
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userName.take(1).uppercase(),
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // User Name
            Text(
                text = userName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black,            )

            // User Email
            Text(
                text = userEmail,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,            )

            Spacer(modifier = Modifier.height(8.dp))

            // City Badge / Chip
            AssistChip(
                onClick = { },
                label = { Text(
                    userCity,
                    color = Color.White,
                ) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(AssistChipDefaults.IconSize)
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                )
            )

            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))

            // Navigation Actions List
            ProfileMenuItem(
                icon = Icons.Default.Park,
                title = "Your Plants",
                onClick = onYourPlantsClick
            )

            ProfileMenuItem(
                icon = Icons.Default.Edit,
                title = "Edit Profile",
                onClick = onEditProfileClick
            )

            ProfileMenuItem(
                icon = Icons.Default.Settings,
                title = "Settings",
                onClick = onSettingsClick
            )

            Spacer(modifier = Modifier.weight(1f))

            VolunteerProfileCard()

            Spacer(modifier = Modifier.height(16.dp))

            // Logout Action (styled distinctly with destructive color)
            AssistChip(
                onClick = {
                    onLogoutClick()
                },
                label = { Text(
                    "LogOut",
                    color = Color.Red,
                ) },
                modifier=Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = null,
                        modifier = Modifier.size(AssistChipDefaults.IconSize)
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                )
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    val contentColor = if (isDestructive) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        if (!isDestructive) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}





@Composable
fun LandingScreen(
    onEnterClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

        ) {
            // Spacer to push content down nicely
            Spacer(modifier = Modifier.height(32.dp))

            // Hero Header Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color= Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Manage your account, view your profile, and explore seamlessly.",
                    style = MaterialTheme.typography.bodyLarge,
                    color= Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

            }

            // Bottom Action Area
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Primary Action: Enter / Sign In
                Button(
                    onClick = onEnterClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Login and Continue",
                        color= Color.Black,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Secondary Action: Create Account
                OutlinedButton(
                    onClick = onCreateAccountClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = "Create Account",
                        color= Color.Black,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

