package com.example.plantbuddy.screens

import android.R.attr.onClick
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.outlined.Bed
import androidx.compose.material.icons.outlined.Countertops // Kitchen icon
import androidx.compose.material.icons.outlined.Living
import androidx.compose.material.icons.outlined.Shower
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.plantbuddy.Screens
import com.example.plantbuddy.component.HomeCategoryCard
import com.example.plantbuddy.component.HomeWeatherCard
import com.example.plantbuddy.component.PlantFactCard
import com.example.plantbuddy.plants.GetAllPlantsState
import com.example.plantbuddy.plants.GetFactState
import com.example.plantbuddy.plants.PlantRepo
import com.example.plantbuddy.plants.PlantViewModel
import com.example.plantbuddy.weather.WeatherViewModel
import kotlinx.coroutines.launch

// Model for grid items
data class HomeCategory(
    val id: String,
    val title: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(mainNavController: NavController) {


    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val repo=PlantRepo()

    val viewModel = PlantViewModel()



    val state by viewModel.getPlantsState.collectAsState()

    val factState by viewModel.getFactState.collectAsState()


    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.getAllPlants()
        viewModel.getFact()
    }




    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(240.dp)
            ) {
                // Header spacing (or add a DrawerHeader here)
                Spacer(modifier = Modifier.height(16.dp))

                NavigationDrawerItem(
                    label = { Text("Saved Facts") },
                    selected = false,
                    onClick = {
                        mainNavController.navigate(Screens.SavedFactsScreen.route)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("Saved Places") },
                    selected = false,
                    onClick = {},
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("Log Out") },
                    selected = false,
                    onClick = {},
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                // Push the close button to the bottom of the drawer
                Spacer(modifier = Modifier.weight(1f))

                NavigationDrawerItem(
                    label = { Text("Close") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("HomeScreen") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open navigation drawer"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Message,
                                contentDescription = "Messages"
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    mainNavController.navigate(Screens.CameraScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Featured Cities Section

                item{
                    HomeWeatherCard(
                        onClick = {}
                    )
                }

               when(factState){
                   is GetFactState.Idle -> {
                       item {
                           Text("Idle")
                       }
                   }
                   is GetFactState.Loading -> {
                       item {
                           Text("Loading")
                       }
                   }
                   is GetFactState.Success -> {

                       val fact= (factState as GetFactState.Success).data

                       item {
                           PlantFactCard(
                               mainNavController = mainNavController,
                               onFactClick = {
                                       mainNavController.currentBackStackEntry
                                           ?.savedStateHandle
                                           ?.set("fact", fact)
                                       mainNavController.navigate(Screens.FactDetails.route)

                               },
                               heading = fact.title,
                               content = fact.fact,
                               category = fact.category
                           )
                       }


                   }
                   is GetFactState.Error -> {
                       item {
                           Text("Failed to load plants")
                       }
                   }

               }



                    item {

                        Text(
                            text = "View All Plants",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                    }

                    when (state) {

                        is GetAllPlantsState.Idle -> {
                            item {
                                Text("Idle")
                            }

                        }

                        is GetAllPlantsState.Loading -> {

                            item {
                                Text("Loading")
                            }
                        }


                        is GetAllPlantsState.Success -> {

                            val plants = (state as GetAllPlantsState.Success).data

                            items(
                                items =plants,
                                key = { it.id }
                            ) { plants ->

                                PlantCard(

                                    onClick= {

                                        mainNavController.navigate("plant_detail/${plants.id}")

                                        Log.d("m", "Clicked on ${plants.name}")
                                        Log.d("m", "Clicked on ${plants.id}")


                                    },
                                    imageUrl = plants.image_url,
                                    name = plants.name,
                                    type = plants.plant_type.name,
                                    scientificName = plants.scientific_name,
                                    lifespan = plants.lifespan.name

                                )

                            }

                        }

                        is GetAllPlantsState.Error -> {

                            Log.d("m" , (state as GetAllPlantsState.Error).message)

                            item {
                                Text(
                                    "Failed to load plants",
                                    modifier = Modifier.padding(12.dp)
                                    )
                            }

                        }

                        else -> {}

                    }



                }
                }

        }
    }



@Composable
fun PlantCard(
    onClick: () -> Unit,
    imageUrl: String,
    name: String,
    type: String,
    scientificName: String,
    lifespan: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable{onClick()}
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = androidx.compose.ui.graphics.Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Log.d("IMAGE_URL", imageUrl)

            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier
                    .size(110.dp)
                    .clip(RoundedCornerShape(14.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = androidx.compose.ui.graphics.Color(0xFF2E7D32),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                DetailRow(
                    label = "Type",
                    value = type
                )

                Spacer(modifier = Modifier.height(6.dp))

                DetailRow(
                    label = "Scientific",
                    value = scientificName
                )

                Spacer(modifier = Modifier.height(6.dp))

                DetailRow(
                    label = "Lifespan",
                    value = lifespan
                )
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.LocalFlorist,
            contentDescription = null,
            tint = androidx.compose.ui.graphics.Color(0xFF66BB6A),
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = "$label:",
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            color = androidx.compose.ui.graphics.Color.Gray
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = value,
            fontSize = 13.sp,
            color = androidx.compose.ui.graphics.Color(0xFF424242),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Preview
@Composable
fun PreviewHome(){
    HomeScreen(mainNavController = rememberNavController())
}

