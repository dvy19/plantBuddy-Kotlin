package com.example.plantbuddy


import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.plantbuddy.camera.AddNoteScreen
import com.example.plantbuddy.camera.CameraScreen
import com.example.plantbuddy.component.BottomNav
import com.example.plantbuddy.plants.Fact
import com.example.plantbuddy.screens.FactDetails
import com.example.plantbuddy.screens.HomeScreen
import com.example.plantbuddy.screens.PlantDetailScreen
import com.example.plantbuddy.screens.SavedFactsScreen
import com.example.plantbuddy.screens.SavedPhotosScreen
import com.example.plantbuddy.screens.SplashScreen


@Composable
fun RootNav(innerPadding: PaddingValues) {
    val mainNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNav(mainNavController)
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
        navController = mainNavController,
        startDestination = Screens.HomeScreen.route
    ) {


        composable("splash") {
            SplashScreen(mainNavController)
        }

        composable(Screens.HomeScreen.route){
            HomeScreen(mainNavController = mainNavController)
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

        composable(Screens.SavedPhotosScreen.route){
            SavedPhotosScreen(mainNavController = mainNavController)
        }



    }
}}

