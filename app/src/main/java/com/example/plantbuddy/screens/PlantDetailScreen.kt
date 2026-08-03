package com.example.plantbuddy.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.plantbuddy.FAQRetrofit.FaqRequest
import com.example.plantbuddy.FAQRetrofit.FaqViewModel
import com.example.plantbuddy.FAQRetrofit.GetFaqState
import com.example.plantbuddy.Screens
import com.example.plantbuddy.component.FaqItem
import com.example.plantbuddy.component.FaqScreenContainer
import com.example.plantbuddy.plants.GetSinglePlantState
import com.example.plantbuddy.plants.Plant
import com.example.plantbuddy.plants.PlantRepo
import com.example.plantbuddy.plants.PlantVMFac
import com.example.plantbuddy.plants.PlantViewModel
import com.example.plantbuddy.room.DatabaseProvider
import com.example.plantbuddy.room.SavedFactRepository
import com.example.plantbuddy.room.SavedFactViewModel
import com.example.plantbuddy.room.SavedViewModelFac
import com.example.plantbuddy.room.wishlist.PlantEntity
import com.example.plantbuddy.room.wishlist.PlantRoomRepo
import com.example.plantbuddy.room.wishlist.PlantRoomViewModel
import com.example.plantbuddy.room.wishlist.PlantRoomViewModelFact
import com.example.plantbuddy.room.wishlist.SavedPlantState


// --- Custom Theme Colors ---
val ForestGreen = Color(0xFF1B4332)
val LightSage = Color(0xFFE8F5E9)
val MutedSage = Color(0xFFD8F3DC)
val SurfaceWhite = Color(0xFFFFFFFF)
val SoftBackground = Color(0xFFF8F9FA)
val TextMuted = Color(0xFF6C757D)

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlantDetailScreen(
    mainNavController: NavController,
    plant_id:Int?,
    onBackClick: () -> Unit = {},
    onSaveOfflineClick: (Plant) -> Unit = {}
) {
    var isLiked by remember { mutableStateOf(false) }

    val context= LocalContext.current


// Wrap in remember so we don't recreate instances on every frame/recomposition
    val viewModel: PlantViewModel = viewModel(
        factory = remember(context) {
            val database = DatabaseProvider.getDatabase(context.applicationContext)
            val repository = PlantRepo(database.dailyFactDao())
            PlantVMFac(repository)
        }
    )

    val faqViewModel: FaqViewModel=viewModel()

    val faqState by faqViewModel.getFaqState.collectAsState()





    val sampleFaqs = listOf(
        FaqItem(
            id = 1,
            question = "Give me 5 facts about this plant. ",

        ),
        FaqItem(
            id = 2,
            question = "How to take care of this plant?",

        )

    )

    var selectedFaq by remember {
        mutableStateOf<FaqItem?>(null)
    }



    val plantRoomViewModel: PlantRoomViewModel = viewModel(
        factory = remember(context) {
            val database = DatabaseProvider.getDatabase(context.applicationContext)
            val repository = PlantRoomRepo(database.plantDao())
            PlantRoomViewModelFact(repository)
        }
    )

    val savePlantState by plantRoomViewModel.savePlantState.collectAsState()


    Log.d("m",plant_id.toString())

    LaunchedEffect(plant_id){

        plant_id?.let{
            viewModel.getSinglePlant(plant_id)
        }
    }


    val singlePlantState by viewModel.getSinglePlantState.collectAsState()


    when(val state=singlePlantState){

        is GetSinglePlantState.Idle -> {
            Text("Idle")
        }
        is GetSinglePlantState.Loading -> {
            Text("Loading")


        }

        is GetSinglePlantState.Success -> {
            val plant = state.data


            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text("Plant Details")
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    mainNavController.popBackStack()
                                },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(SurfaceWhite.copy(alpha = 0.8f), CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Navigate Back",
                                    tint = TextDark
                                )
                            }


                        },

                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        plantRoomViewModel.savePlantOffline(
                            plant= PlantEntity(
                                id = plant.id,
                                name = plant.name,
                                image_url = plant.image_url,
                                description = plant.description,
                                homePlace = "home",
                                type = plant.plant_type.name

                            )
                        )
                    }) {
                        when(savePlantState){
                            is SavedPlantState.Loading -> CircularProgressIndicator()
                            is SavedPlantState.Success -> Icon(imageVector = Icons.Default.Bookmark, contentDescription = "Save Offline")
                            is SavedPlantState.Error -> {
                                Icon(imageVector = Icons.Default.BookmarkBorder, contentDescription = "Save Offline")
                            }
                            else -> Icon(imageVector = Icons.Default.BookmarkBorder, contentDescription = "Save Offline")
                        }

                        LaunchedEffect(savePlantState) {
                            if (savePlantState is SavedPlantState.Error) {
                                Toast.makeText(
                                    context,
                                    "Save Failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                },
                /*
                bottomBar = {
                    // Sticky Save Offline Button
                    Surface(
                        shadowElevation = 12.dp,
                        color = SurfaceWhite
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 16.dp)
                                .navigationBarsPadding()
                        ) {
                            Button(
                                onClick = {

                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ForestGreen,
                                    contentColor = Color.White
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.FileDownload,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Save Plant Offline",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }

                 */
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(SoftBackground)
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    // --- Header Image with Floating Card Style ---
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                    ) {
                        AsyncImage(
                            model = plant.image_url,
                            contentDescription = plant.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    // --- Primary Information Header ---
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-24).dp),
                        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 24.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = plant.name,
                                        fontSize = 26.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextDark
                                    )
                                    Text(
                                        text = plant.scientific_name,
                                        fontSize = 15.sp,
                                        fontStyle = FontStyle.Italic,
                                        color = TextMuted
                                    )
                                }

                                // Like Button inside the screen content
                                IconButton(
                                    onClick = { isLiked = !isLiked },
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(if (isLiked) Color(0xFFFFEBEE) else LightSage)
                                ) {
                                    Icon(
                                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Like Plant",
                                        tint = if (isLiked) Color(0xFFE53935) else ForestGreen
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = plant.description,
                                fontSize = 14.sp,
                                color = TextDark.copy(alpha = 0.8f),
                                lineHeight = 20.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Quick Feature Tags (Badges)
                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (plant.pet_friendly) QuickBadge(
                                    "Pet Friendly",
                                    Icons.Outlined.Pets
                                )
                                if (plant.air_purifying) QuickBadge(
                                    "Air Purifying",
                                    Icons.Outlined.Air
                                )
                                if (plant.edible) QuickBadge("Edible", Icons.Outlined.Restaurant)
                                if (plant.pruning_required) QuickBadge(
                                    "Pruning Needed",
                                    Icons.Outlined.ContentCut
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // --- Care Summary Grid ---
                            Text(
                                text = "Care Essentials",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                CareMetricCard(
                                    icon = Icons.Outlined.WbSunny,
                                    title = "Light",
                                    value = plant.light_requirement.name,
                                    modifier = Modifier.weight(1f)
                                )
                                CareMetricCard(
                                    icon = Icons.Outlined.WaterDrop,
                                    title = "Water",
                                    value = plant.water_requirement.name,
                                    modifier = Modifier.weight(1f)
                                )
                                CareMetricCard(
                                    icon = Icons.Outlined.Thermostat,
                                    title = "Temp",
                                    value = "${plant.temperature_min}° - ${plant.temperature_max}°C",
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            // --- Detailed Characteristics ---
                            Text(
                                text = "Botanical Profile",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDark
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            DetailRow("Category", plant.category.name)
                            DetailRow("Plant Type", plant.plant_type.name)
                            DetailRow("Growth Rate", plant.growth_rate.name)
                            DetailRow("Lifespan", plant.lifespan.name)
                            DetailRow("Soil Type", plant.soil_type.name)
                            DetailRow("Average Height", plant.average_height)
                            DetailRow("Humidity Need", plant.humidity)
                            DetailRow("Fertilizer", plant.fertilizer)
                            DetailRow("Repotting", plant.repotting_frequency)
                            DetailRow("Planting Season", plant.best_planting_season.name)

                            plant.flowering_season?.let {
                                DetailRow("Flowering Season", it.name)
                            }
                            plant.fruiting_season?.let {
                                DetailRow("Fruiting Season", it.name)
                            }

                            FaqScreenContainer(
                                faqList = sampleFaqs,
                                modifier = Modifier.padding(horizontal = 20.dp),
                                onFaqClick = {
                                    selectedFaq = it

                                    faqViewModel.getFaq(
                                        request = FaqRequest(
                                            question_id = it.id,
                                            plant_id = plant.id
                                        )
                                    )
                                }
                            )

                            if (selectedFaq != null) {

                                AlertDialog(
                                    onDismissRequest = {
                                        selectedFaq = null
                                    },

                                    title = {
                                        Text(selectedFaq!!.question)
                                    },

                                    text = {
                                        when(faqState){

                                            is GetFaqState.Idle -> {
                                                Text("Idle")
                                            }
                                            is GetFaqState.Loading -> {
                                                Text("Loading")

                                            }

                                            is GetFaqState.Success -> {
                                                val faqData=(faqState as GetFaqState.Success).data
                                                //Log.d("m",data.toString())

                                                Text(faqData.answer)
                                            }

                                            is GetFaqState.Error -> {

                                            }

                                        }
                                    },

                                    confirmButton = {

                                        TextButton(
                                            onClick = {
                                                selectedFaq = null
                                            }
                                        ) {
                                            Text("Close")
                                        }

                                    }
                                )
                            }
                        }
                    }
                }
            }

        }

        is GetSinglePlantState.Error -> {
            Text("Failed to load plant")
        }




    }
}



@Composable
private fun QuickBadge(label: String, icon: ImageVector) {
    Surface(
        color = LightSage,
        shape = RoundedCornerShape(50)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ForestGreen,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = ForestGreen
            )
        }
    }
}

@Composable
private fun CareMetricCard(
    icon: ImageVector,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(SoftBackground, RoundedCornerShape(16.dp))
            .border(1.dp, LightSage, RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(MutedSage, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = ForestGreen,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            fontSize = 12.sp,
            color = TextMuted
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = TextDark
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = TextMuted
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextDark
        )
    }
    HorizontalDivider(color = SoftBackground, thickness = 1.dp)
}

