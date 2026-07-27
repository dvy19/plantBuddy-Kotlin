package com.example.plantbuddy.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.plantbuddy.room.SavedFactViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.plantbuddy.component.PlantFactCard
import com.example.plantbuddy.room.DatabaseProvider
import com.example.plantbuddy.room.SavedFactRepository
import com.example.plantbuddy.room.SavedViewModelFac

import androidx.lifecycle.viewmodel.compose.viewModel
@Composable
fun SavedFactsScreen(
    mainNavController: NavController
){

    val context = LocalContext.current

// Wrap in remember so we don't recreate instances on every frame/recomposition
    val viewModel: SavedFactViewModel = viewModel(
        factory = remember(context) {
            val database = DatabaseProvider.getDatabase(context.applicationContext)
            val repository = SavedFactRepository(database.savedFactDao())
            SavedViewModelFac(repository)
        }
    )



    val savedFacts by viewModel.savedFacts.collectAsState(emptyList())

    LazyColumn {

        items(savedFacts) { fact ->

            PlantFactCard(
                mainNavController = mainNavController,
                onFactClick = {
                    mainNavController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("fact", fact)
                    mainNavController.navigate("fact_details")
                },
                heading = fact.title,
                content = fact.fact,
                category = fact.category


                )
        }
    }

}

@Preview
@Composable
fun PreviewSavedFactsScreen(){
    SavedFactsScreen(mainNavController = NavController(LocalContext.current))

}