package com.example.plantbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.plantbuddy.ui.theme.PlantBuddyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlantBuddyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RootNav(innerPadding)

                }
            }
        }
    }
}


sealed class Screens( var route:String){

    data object SplashScreen:Screens("splash")
    data object HomeScreen:Screens("home")

    data object FactDetails:Screens("fact_detail")

    data object PlantDetailScreen : Screens("plant_detail/{id}")

    data object SavedFactsScreen : Screens("saved_facts")

    data object CameraScreen:Screens("camera_screen")

    data object AddNoteScreen:Screens("add_note")

    data object SavedPhotosScreen:Screens("saved_photos")

    data object WishlistScreen:Screens("wishlist")

    data object RegisterScreen:Screens("signup")

    data object LoginScreen:Screens("login")

    data object GetStartScreen:Screens("get_start")


    data object UserProfileScreenLayout:Screens("profile-layout")

    data object UserDetailsScreen:Screens("profile")

    data object WaterStreakScreen:Screens("water_streak/{id}")

    data object PlantCatalogScreen:Screens("plant_catalog")

    data object YourPlantsScreen:Screens("your_plant")

    data object NgoDetailsScreen:Screens("ngo_details")
    data object NgoRegistrationForm:Screens("ngo_registration")
    data object NgoHomeScreen:Screens("ngo_home")
    data object NgoCreateCampaignScreen:Screens("ngo_create_campaign")
    data object NgoLoginScreen:Screens("ngo_login")
    data object NgoProfileScreen:Screens("ngo_profile")

}
