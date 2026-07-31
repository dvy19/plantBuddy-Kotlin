package com.example.plantbuddy.room.PersonalPlant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plantbuddy.component.PlantFactCard
import com.example.plantbuddy.room.DatabaseProvider
import com.example.plantbuddy.room.SavedFactRepository
import com.example.plantbuddy.room.SavedFactViewModel
import com.example.plantbuddy.room.SavedViewModelFac
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.plantbuddy.component.PlantGridCard


@Composable
fun YourPlantsScreen(
    mainNavController: NavController
){


    val context = LocalContext.current

// Wrap in remember so we don't recreate instances on every frame/recomposition
    val viewModel: PersonalPlantVM = viewModel(
        factory = remember(context) {
            val database = DatabaseProvider.getDatabase(context.applicationContext)
            val repository = PersonalPlantRepo(database.personalPlantDao())
            PersonalPlantVMFac(repository)
        }
    )

    val getPersonalPlants by viewModel.getPersonalPlants.collectAsState(emptyList())

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(
            items = getPersonalPlants,
            key = { plant -> plant.plant_id }
        ) { plant ->
            PlantGridCard(
                plantName = plant.plant_name,
                plantType = plant.plant_type,
                imageUrl = plant.image_url,
                //isAdded = plant.isAdded,
                onAddClick = {


                },

                onNavigate = {

                    mainNavController.navigate("water_streak/${plant.plant_id}")
                }
            )
        }
    }
}
