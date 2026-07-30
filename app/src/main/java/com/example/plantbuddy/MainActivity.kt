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

    data object SignupScreen:Screens("signup")

    data object LoginScreen:Screens("login")


    data object UserProfileScreenLayout:Screens("profile-layout")

}
