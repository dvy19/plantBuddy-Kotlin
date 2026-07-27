package com.example.plantbuddy.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.plantbuddy.room.SavedFactViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plantbuddy.component.PlantFactCard
import com.example.plantbuddy.room.DatabaseProvider
import com.example.plantbuddy.room.SavedFactRepository
import com.example.plantbuddy.room.SavedViewModelFac

import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.plantbuddy.camera.Charcoal
import com.example.plantbuddy.camera.SageGreen
import com.example.plantbuddy.camera.SoftSage
import com.example.plantbuddy.camera.WarmIvory
import com.example.plantbuddy.room.photo.PicRepository
import com.example.plantbuddy.room.photo.PicViewModel
import com.example.plantbuddy.room.photo.PicViewModelFact
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedPhotosScreen(
    mainNavController: NavController
){


    val context=LocalContext.current

    val viewModel: PicViewModel=viewModel(
        factory=remember(context){
            val database=DatabaseProvider.getDatabase(context.applicationContext)
            val repository=PicRepository(database.picDao())
            PicViewModelFact(repository)
    })

    val savedPics by viewModel.savedPics.collectAsState(emptyList())


    fun getMoodLabel(moodEmoji: String): String {
        return when (moodEmoji) {
            "😊" -> "Happy"
            "🌿" -> "Peaceful"
            "🧘" -> "Calm"
            "☕" -> "Cozy"
            "💭" -> "Thoughtful"
            "😴" -> "Tired"
            "⚡" -> "Energetic"
            "🌧️" -> "Gloomy"
            else -> "Journal" // Fallback default
        }
    }


    // 1. Define your filter options (adding 'All' at the start)
    val filterOptions = listOf("All") + listOf("😊", "🌿", "🧘", "☕", "💭", "😴", "⚡", "🌧️")

    var selectedFilter by remember { mutableStateOf("All") }

    val filteredPics = remember(selectedFilter, savedPics) {
        if (selectedFilter == "All") {
            savedPics
        } else {
            savedPics.filter { it.mood == selectedFilter }
        }
    }


    LaunchedEffect(savedPics) {
        Log.d("PICS", "savedPics size = ${savedPics.size}")
        savedPics.forEach {
            Log.d("PICS", it.toString())
        }
    }


    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(
                            "My Journal",
                            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                            color = Charcoal
                        )
                        Text(
                            "Capture your moments",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = WarmIvory
                )
            )
        },
        floatingActionButton = {
            // Stylized FAB matching our Sage Green primary accent
            FloatingActionButton(
                onClick = { mainNavController.navigate("camera") },
                containerColor = SageGreen,
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp),
                elevation = FloatingActionButtonDefaults.elevation(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New Entry",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        containerColor = WarmIvory
    ) { padding ->

        if (savedPics.isEmpty()) {
            // Elegant Empty State
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Book,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = SageGreen.copy(alpha = 0.4f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Your diary is empty.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                    Text(
                        "Tap + to write your first story.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp) // Generous spacing for a premium feel
            ) {

                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items(filterOptions) { moodItem ->
                            val isSelected = selectedFilter == moodItem

                            Surface(

                                modifier = Modifier
                                    .clickable { selectedFilter = moodItem },
                                shape = RoundedCornerShape(50.dp),
                                color = if (isSelected) SageGreen else Color.White,
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = if (isSelected) Color.Transparent else Charcoal.copy(alpha = 0.1f)
                                ),
                                tonalElevation = if (isSelected) 4.dp else 0.dp
                            ) {
                                Row(
                                    modifier = Modifier.padding(
                                        horizontal = 14.dp,
                                        vertical = 8.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    if (moodItem == "All") {
                                        Text(
                                            text = "✨ All Entries",
                                            style = MaterialTheme.typography.labelLarge.copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = if (isSelected) Color.White else Charcoal
                                        )
                                    } else {
                                        Text(
                                            text = moodItem,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = getMoodLabel(moodItem),
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = FontWeight.SemiBold
                                            ),
                                            color = if (isSelected) Color.White else Charcoal.copy(
                                                alpha = 0.8f
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Item 2: Filter Empty State OR Active Lists
                if (filteredPics.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 60.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("📭", style = MaterialTheme.typography.displayMedium)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = "No entries logged as '${getMoodLabel(selectedFilter)}'",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                } else {
                    // Item 3: Populating Cards out of our runtime dynamic filtered reference map
                    items(
                        items = filteredPics,
                        key = { it.id } // Adding keys prevents UI jitter on updates
                    ) { journal ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { mainNavController.navigate("edit/${journal.id}") },
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column {
                                // Image takes top half cleanly
                                Image(
                                    painter = rememberAsyncImagePainter(File(journal.imagePath)),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp),
                                    contentScale = ContentScale.Crop
                                )

                                // Text details container
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    // 1. Details Ribbon (Date, Time, and Mood)
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween // Pushes mood badge to the right side
                                    ) {
                                        // Left Side: Date & Time Left Containers
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Surface(
                                                color = SoftSage,
                                                shape = RoundedCornerShape(6.dp)
                                            ) {
                                                Text(
                                                    text = journal.date, // Note: update to journal.currentDate if that's what your Entity uses
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontWeight = FontWeight.Bold
                                                    ),
                                                    color = SageGreen,
                                                    modifier = Modifier.padding(
                                                        horizontal = 8.dp,
                                                        vertical = 4.dp
                                                    )
                                                )
                                            }

                                            Text(
                                                text = "•  ${journal.time}", // Note: update to journal.currentTime if that's what your Entity uses
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color.Gray
                                            )
                                        }

                                        // NEW: Mood Badge Component (Sticker + Text stacked vertically)
                                        Surface(
                                            color = Charcoal.copy(alpha = 0.05f), // subtle neutral background tint
                                            shape = RoundedCornerShape(12.dp)
                                        ) {
                                            Column(
                                                modifier = Modifier.padding(
                                                    horizontal = 10.dp,
                                                    vertical = 4.dp
                                                ),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Text(
                                                    text = journal.mood, // Displays the emoji sticker
                                                    style = MaterialTheme.typography.titleMedium
                                                )
                                                Text(
                                                    text = getMoodLabel(journal.mood), // Displays text description under it
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontSize = 9.sp,
                                                        fontWeight = FontWeight.Bold
                                                    ),
                                                    color = Charcoal.copy(alpha = 0.6f)
                                                )
                                            }
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // 2. Journal Snippet text
                                    Text(
                                        text = journal.note,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Medium,
                                            color = Charcoal
                                        ),
                                        maxLines = 3, // Prevents text overflow breaks in the grid view
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }



}

