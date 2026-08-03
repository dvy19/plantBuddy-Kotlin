package com.example.plantbuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.plantbuddy.room.SavedFactViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.plantbuddy.component.PlantFactCard
import com.example.plantbuddy.room.DatabaseProvider
import com.example.plantbuddy.room.SavedFactRepository
import com.example.plantbuddy.room.SavedViewModelFac

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantbuddy.NGO.Screens.SoftLeafGreen
import com.example.plantbuddy.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.heightIn(min = 56.dp)
                    .background(SoftLeafGreen),
                title = { Text("Offline Saved Facts") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            mainNavController.popBackStack()

                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Open navigation drawer"
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                ),
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

    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.background(Color.White)
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {

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

}

@Preview
@Composable
fun PreviewSavedFactsScreen(){
    SavedFactsScreen(mainNavController = NavController(LocalContext.current))

}