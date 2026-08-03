package com.example.plantbuddy


import WaterStreakScreen
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.plantbuddy.NGO.campaign.NgoCreateCampaign
import com.example.plantbuddy.NGO.NgoHome
import com.example.plantbuddy.NGO.NgoRegistrationScreen
import com.example.plantbuddy.NGO.details.NgoDetailsForm
import com.example.plantbuddy.auth.LoginScreen
import com.example.plantbuddy.auth.RegisterScreen
import com.example.plantbuddy.camera.AddNoteScreen
import com.example.plantbuddy.camera.CameraScreen
import com.example.plantbuddy.component.BottomNav
import com.example.plantbuddy.plants.Fact
import com.example.plantbuddy.room.PersonalPlant.YourPlantsScreen
import com.example.plantbuddy.screens.FactDetails
import com.example.plantbuddy.screens.HomeScreen
import com.example.plantbuddy.screens.PlantCatalogScreen
import com.example.plantbuddy.screens.PlantDetailScreen
import com.example.plantbuddy.screens.SavedFactsScreen
import com.example.plantbuddy.screens.SavedPhotosScreen
import com.example.plantbuddy.screens.SplashScreen
import com.example.plantbuddy.screens.profile.UserProfileScreenLayout
import com.example.plantbuddy.userDetails.UserDetailsScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.plantbuddy.NGO.NgoBottomNavBar
import com.example.plantbuddy.NGO.NgoLoginScreen
import com.example.plantbuddy.NGO.NgoProfileScreen
import com.example.plantbuddy.NGO.Screens.NgoDetailScreen
import com.example.plantbuddy.NGO.campaign.Campaign
import com.example.plantbuddy.screens.community.CampaignDetailScreen
import com.example.plantbuddy.screens.community.CommunityScreen
import com.example.plantbuddy.screens.community.NgoAllActiveCampaigns

@Composable
fun RootNav(innerPadding: PaddingValues) {
    val mainNavController = rememberNavController()

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarRoutes = listOf(

        Screens.HomeScreen.route,
    )

    val ngoRoutes=listOf(
        Screens.NgoHomeScreen.route,
        Screens.NgoCreateCampaignScreen.route,

    )

    Scaffold(
        bottomBar = {

            if (currentRoute in bottomBarRoutes) {
                BottomNav(mainNavController)
            }
            else{
                if(currentRoute in ngoRoutes){
                    NgoBottomNavBar(mainNavController)
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
        navController = mainNavController,
        startDestination = Screens.SplashScreen.route
    ) {



            composable(Screens.UserProfileScreenLayout.route){
                UserProfileScreenLayout(mainNavController)
            }

            composable(Screens.PlantCatalogScreen.route){
                PlantCatalogScreen(mainNavController)
            }

            composable(Screens.CommunityScreen.route){
                CommunityScreen(mainNavController)
            }


            composable(Screens.GetStartScreen.route) {
                GetStartScreen(
                    onUserSelected = {
                        mainNavController.navigate(Screens.HomeScreen.route)
                    },
                    onNgoSelected = {
                        mainNavController.navigate(Screens.NgoRegistrationForm.route)
                    }
                )
            }

            composable(Screens.NgoDetailsScreen.route){
                NgoDetailsForm(mainNavController)
            }

            composable(Screens.NgoRegistrationForm.route){
                NgoRegistrationScreen(mainNavController)
            }

            composable(Screens.NgoHomeScreen.route){
                NgoHome(mainNavController)
            }


            composable(
                route = Screens.NgoDetailScreen.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val id = backStackEntry.arguments?.getInt("id")

                    NgoDetailScreen(
                    mainNavController = mainNavController,
                    ngoId= id
                )
            }


            composable(
                route = Screens.CampaignDetailScreen.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val id = backStackEntry.arguments?.getInt("id")

                CampaignDetailScreen(
                    mainNavController = mainNavController,
                    campaign_id= id,

                    )
            }




            composable(
                route = Screens.NgoAllActiveCampaigns.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val id = backStackEntry.arguments?.getInt("id")

                NgoAllActiveCampaigns(
                    mainNavController = mainNavController,
                    ngo_id= id
                )
            }

            composable(Screens.NgoCreateCampaignScreen.route){
                NgoCreateCampaign(mainNavController)
            }

            composable(Screens.NgoLoginScreen.route){
                NgoLoginScreen(mainNavController)
            }

            composable(Screens.NgoProfileScreen.route){
                NgoProfileScreen(mainNavController)
            }

            composable(Screens.RegisterScreen.route) {
                RegisterScreen(
                    mainNavController,
                    onNavigateToLogin = {
                        mainNavController.navigate("login")
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            composable(Screens.LoginScreen.route) {
                LoginScreen(
                    mainNavController,
                    onNavigateToRegister = {
                        mainNavController.navigate("signup")
                    },
                    modifier = Modifier.padding(innerPadding),
                )
            }



        composable("splash") {
            SplashScreen(mainNavController)
        }

        composable(Screens.HomeScreen.route){
            HomeScreen(mainNavController = mainNavController)
        }

            composable(Screens.UserDetailsScreen.route){
                UserDetailsScreen(mainNavController)
            }

        composable(Screens.CameraScreen.route){
            CameraScreen(mainNavController)
        }

        composable("fact_detail") {

            val fact =
                mainNavController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<Fact>("fact")

            if (fact != null) {
                FactDetails(fact)
            }
        }

        composable(Screens.SavedFactsScreen.route){
            SavedFactsScreen(mainNavController = mainNavController)
        }



        composable(
            "add_note/{imagePath}"
        ) { backStackEntry ->

            val imagePath =
                Uri.decode(
                    backStackEntry.arguments
                        ?.getString("imagePath")
                )

            AddNoteScreen(
                mainNavController,
                imagePath!!,
            )
        }

        composable(
            route = Screens.PlantDetailScreen.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id")

            PlantDetailScreen(
                mainNavController = mainNavController,
                plant_id= id
            )
        }


            composable(
                route = Screens.WaterStreakScreen.route,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->

                val id = backStackEntry.arguments?.getInt("id")

                WaterStreakScreen(
                    mainNavController = mainNavController,
                    plantId= id,
                )
            }

            composable(Screens.YourPlantsScreen.route){
                YourPlantsScreen(mainNavController = mainNavController)
            }

        composable(Screens.SavedPhotosScreen.route){
            SavedPhotosScreen(mainNavController = mainNavController)
        }



    }
}}

