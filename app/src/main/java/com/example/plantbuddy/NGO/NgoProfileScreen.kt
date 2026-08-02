package com.example.plantbuddy.NGO

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.plantbuddy.NGO.details.GetNgoProfileState
import com.example.plantbuddy.NGO.details.NgoDetailsRepo
import com.example.plantbuddy.NGO.details.NgoDetailsVM
import com.example.plantbuddy.Screens
import com.example.plantbuddy.auth.SessionManager
import com.example.plantbuddy.screens.profile.ProfileScreen
import com.example.plantbuddy.userDetails.DetailViewModel
import com.example.plantbuddy.userDetails.GetProfileState
import com.example.plantbuddy.userDetails.UserDetailRepo


@Composable
fun NgoProfileScreen(
    mainNavController: NavController
) {


    val context=LocalContext.current

    val sessionManager= SessionManager(context)

    val repo= NgoDetailsRepo(sessionManager)

    val viewModel: NgoDetailsVM=viewModel(
        factory=viewModelFactory {
            NgoDetailsVM(repo)
        }
    )

        LaunchedEffect(Unit) {
            Log.d("Compose", "Calling API")
            viewModel.getProfile()


        }

    val getProfileState by viewModel.getProfileState.collectAsState()

        //Log.d("m",getProfileState.toString())

        when(getProfileState) {

            is GetNgoProfileState.Idle -> {
                Log.d("m", getProfileState.toString())

            }
            is GetNgoProfileState.Loading -> {
                Log.d("m", getProfileState.toString())

                CircularProgressIndicator()
            }
            is GetNgoProfileState.Success -> {
                Log.d("m", getProfileState.toString())

                val userdata = (getProfileState as GetProfileState.Success).data


                ProfileScreen(
                    userCity = userdata.city,
                    userName = userdata.name,
                    userEmail = "NA",

                    onLogoutClick = {
                        sessionManager.logout()
                    },
                    onYourPlantsClick = {
                        mainNavController.navigate(Screens.PlantCatalogScreen.route)
                        }
                )
            }
            is GetNgoProfileState.Error -> {
                Log.d("m", getProfileState.toString())

                Text((getProfileState as GetProfileState.Error).message)
            }

        }


}


