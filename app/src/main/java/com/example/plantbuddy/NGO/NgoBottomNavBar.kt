package com.example.plantbuddy.NGO


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.plantbuddy.Screens

@Composable
fun NgoBottomNavBar(
    mainNavController: NavHostController
) {

    val currentRoute =
        mainNavController.currentBackStackEntryAsState().value?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        contentAlignment = Alignment.BottomCenter
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),

                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                BottomItem(
                    icon = Icons.Default.Home,
                    selected = currentRoute == Screens.NgoHomeScreen.route
                ) {
                    mainNavController.navigate(Screens.NgoHomeScreen.route)
                }





                BottomItem(
                    icon = Icons.Default.Add,
                    selected = currentRoute == Screens.NgoCreateCampaignScreen.route
                ) {
                    mainNavController.navigate(Screens.NgoCreateCampaignScreen.route) {
                        popUpTo(mainNavController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }

/*
                BottomItem(
                    icon = Icons.Default.AccountCircle,
                    selected = currentRoute == Screens.UserProfileScreenLayout.route
                ) {
                    mainNavController.navigate(Screens.UserProfileScreenLayout.route) {
                        popUpTo(mainNavController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }

 */





            }
        }
    }
}



@Composable
fun BottomItem(
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .clickable(onClick = onClick)
            .background(
                if (selected)
                    Color(0xFFEDE3FF)
                else
                    Color.Transparent
            )
            .padding(
                horizontal = 22.dp,
                vertical = 10.dp
            )
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (selected)
                Color(0xFF6C2BEE)
            else
                Color.Gray
        )
    }
}
