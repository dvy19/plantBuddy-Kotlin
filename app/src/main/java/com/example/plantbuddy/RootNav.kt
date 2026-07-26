package com.example.plantbuddy


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.plantbuddy.plants.Fact
import com.example.plantbuddy.screens.FactDetails
import com.example.plantbuddy.screens.HomeScreen
import com.example.plantbuddy.screens.PlantDetailScreen
import com.example.plantbuddy.screens.SplashScreen


@Composable
fun RootNav(innerPadding: PaddingValues) {
    val mainNavController = rememberNavController()

    NavHost(
        navController = mainNavController,
        startDestination = Screens.HomeScreen.route
    ) {


        composable("splash") {
            SplashScreen(mainNavController)
        }

        composable(Screens.HomeScreen.route){
            HomeScreen(mainNavController = mainNavController)
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



    }
}