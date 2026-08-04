package com.example.plantbuddy.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.plantbuddy.auth.SessionManager
import com.example.plantbuddy.component.PlantGridCard
import com.example.plantbuddy.plants.GetAllPlantsState
import com.example.plantbuddy.plants.PlantRepo
import com.example.plantbuddy.plants.PlantVMFac
import com.example.plantbuddy.plants.PlantViewModel
import com.example.plantbuddy.room.DatabaseProvider
import com.example.plantbuddy.room.PersonalPlant.PersonalPlantEntity
import com.example.plantbuddy.room.PersonalPlant.PersonalPlantRepo
import com.example.plantbuddy.room.PersonalPlant.PersonalPlantVM
import com.example.plantbuddy.room.PersonalPlant.PersonalPlantVMFac
import com.example.plantbuddy.room.PersonalPlant.SavePersonalPlantState
import kotlinx.coroutines.delay

// Custom Color Palette Constants

val DarkGreenText = Color(0xFF1B5E20)
val LightGreenBg = Color(0xFFF1F8E9)
val MintChip = Color(0xFFE8F5E9)




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantCatalogScreen(
    mainNavController: NavController
) {
    // Search bar state
    var searchQuery by remember { mutableStateOf("") }


    val context= LocalContext.current
    val sessionManager=SessionManager(context)

    val gridState = rememberLazyGridState()
// Wrap in remember so we don't recreate instances on every frame/recomposition
    val personalViewModel: PersonalPlantVM = viewModel(
        factory = remember(context) {
            val database = DatabaseProvider.getDatabase(context.applicationContext)
            val repository = PersonalPlantRepo(database.personalPlantDao())
            PersonalPlantVMFac(repository)
        }
    )


// Wrap in remember so we don't recreate instances on every frame/recomposition
    val viewModel: PlantViewModel = viewModel(
        factory = remember(context) {
            val database = DatabaseProvider.getDatabase(context.applicationContext)
            val repository = PlantRepo(database.dailyFactDao())
            PlantVMFac(repository)
        }

    )

    val savePersonalState by personalViewModel.savePersonalPlantState.collectAsState()


    LaunchedEffect(searchQuery) {
        delay(500)

        viewModel.getAllPlants(
            search = searchQuery
        )
    }

    when(savePersonalState){
        is SavePersonalPlantState.Idle -> {}
        is SavePersonalPlantState.Loading -> {}
        is SavePersonalPlantState.Success -> {
            Toast.makeText(context, "Plant Added", Toast.LENGTH_SHORT).show()
        }
        is SavePersonalPlantState.Error -> {}
    }


    val state by viewModel.getPlantsState.collectAsState()
    // Keep Scaffold and Search Bar OUTSIDE the state block so UI structure stays fixed
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Find All Plants ",
                        color = TextDark
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        mainNavController.popBackStack()
                    }) {

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigate back",
                            tint = EmeraldPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SoftWhiteBackground
                )
            )
        },
        containerColor = Color(0xFFF8FAF7)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 1. Search Bar (Always Visible)
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                placeholder = { Text("Search plants...", color = Color.Gray, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = ForestGreen
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear Search",
                                tint = Color.Gray
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,

                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,

                    cursorColor = ForestGreen,

                    focusedBorderColor = ForestGreen,
                    unfocusedBorderColor = Color.Gray,

                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray
                )
            )

            // 2. Dynamic Content Area based on UI State
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (val currentState = state) {
                    is GetAllPlantsState.Idle -> {
                        Text("Ready to fetch plants...", color = Color.Gray)
                    }

                    is GetAllPlantsState.Loading -> {
                        CircularProgressIndicator(color = ForestGreen)
                    }

                    is GetAllPlantsState.Success -> {
                        val allPlants = currentState.data






                        if (allPlants.isEmpty()) {
                            Text(text = "No plants found", color = Color.Gray, fontSize = 14.sp)
                        } else
                        {

                            allPlants.forEach {
                                Log.d("Plant", "id=${it.id}, name=${it.name}")
                            }

                            LazyVerticalGrid(
                                state=gridState,
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(
                                    items = allPlants,
                                    key = { plant -> plant.id }
                                ) { plant ->
                                    PlantGridCard(
                                        plantName = plant.name,
                                        plantType = plant.plant_type.name,
                                        imageUrl = plant.image_url,
                                        //isAdded = plant.isAdded,
                                        onAddClick = {
                                            personalViewModel.insertPersonalPlant(

                                                PersonalPlantEntity(
                                                    plant_id = plant.id,
                                                    plant_name = plant.name,
                                                    plant_type = plant.plant_type.name,
                                                    water_requirement = plant.water_requirement.name,
                                                    image_url = plant.image_url
                                                )

                                            )
                                        },
                                        onNavigate = {

                                        }
                                    )
                                }
                            }
                        }

                        LaunchedEffect(gridState) {
                            snapshotFlow {
                                val layoutInfo = gridState.layoutInfo

                                layoutInfo.totalItemsCount -
                                        (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0)
                            }.collect { remainingItems ->

                                if (remainingItems <= 4) {
                                    viewModel.loadNextPage()
                                }
                            }
                        }


                    }
                    is GetAllPlantsState.Error -> {
                        Text(
                            text = currentState.message,
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }



                        }
                    }
                }
            }





// ---------------------------------------------------------------------------
// 3. PLANT GRID CARD COMPONENT
// ---------------------------------------------------------------------------
@Composable
fun PlantGridCard(
    plantName: String,
    plantType: String,
    imageUrl: String,
    isAdded: Boolean = false,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Plant Image Box with Top-Right Action Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightGreenBg)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = plantName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Add to Personal Plants Button
                IconButton(
                    onClick = onAddClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(6.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            if (isAdded) ForestGreen else Color.White.copy(alpha = 0.9f)
                        )
                ) {
                    Icon(
                        imageVector = if (isAdded) Icons.Default.Check else Icons.Default.Add,
                        contentDescription = "Add Plant",
                        tint = if (isAdded) Color.White else ForestGreen,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Plant Name
            Text(
                text = plantName,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreenText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Plant Type Badge
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = MintChip,
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(
                    text = plantType,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = ForestGreen,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}}