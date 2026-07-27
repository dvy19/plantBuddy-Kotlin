package com.example.plantbuddy.camera



import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.plantbuddy.room.DatabaseProvider
import com.example.plantbuddy.room.photo.OfflinePicEntity
import com.example.plantbuddy.room.photo.PicRepository
import com.example.plantbuddy.room.photo.PicViewModel
import com.example.plantbuddy.room.photo.PicViewModelFact
import com.example.plantbuddy.room.photo.SavedPicState

val SageGreen = Color(0xFF2D5A27)        // Rich, grounded green for primary actions
val SoftSage = Color(0xFFE8F0E6)         // Light, soothing green for backgrounds
val TerracottaOrange = Color(0xFFD96B43) // Vibrant but mature orange for deletions/accents
val WarmIvory = Color(0xFFFCFBF9)        // Soft white for the app background
val Charcoal = Color(0xFF232323)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    mainNavController: NavController,
    imagePath: String,

) {

    val context=LocalContext.current

    val viewModel:PicViewModel=viewModel(
        factory = remember(context){
            val database=DatabaseProvider.getDatabase(context.applicationContext)
            val repository=PicRepository(database.picDao())
            PicViewModelFact(repository)
        }
    )

    val savedPicState by viewModel.savePicState.collectAsState()

    val moodOptions = listOf(
        Pair("😊", "Happy"), Pair("🌿", "Peaceful"), Pair("🧘", "Calm"),
        Pair("☕", "Cozy"), Pair("💭", "Thoughtful"), Pair("😴", "Tired"),
        Pair("⚡", "Energetic"), Pair("🌧️", "Gloomy")
    )
    var selectedMood by remember { mutableStateOf(moodOptions.first().first) }

    var note by remember {
        mutableStateOf("")
    }

    val currentDate =
        SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.getDefault()
        ).format(Date())

    val currentTime =
        SimpleDateFormat(
            "hh:mm a",
            Locale.getDefault()
        ).format(Date())
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "New Entry",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Charcoal
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { mainNavController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Charcoal)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = WarmIvory)
            )
        },
        floatingActionButton = {
            // Prominent Sage Green FAB to save the entry
            ExtendedFloatingActionButton(
                onClick = {


                    val pic= OfflinePicEntity(
                        imagePath = imagePath,
                        note = note,
                        plant_name = "",
                        savedAt = System.currentTimeMillis(),
                        mood = selectedMood,
                        date = currentDate,
                        time = currentTime
                    )

                    viewModel.savePic(pic)


                },
                containerColor = SageGreen,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(4.dp)
            ) {
                Icon(Icons.Default.Done, contentDescription = "Save Entry")
                Spacer(modifier = Modifier.width(8.dp))

                when(savedPicState){
                    is SavedPicState.Idle->{
                        Text("Save")
                    }

                    is SavedPicState.Loading->{
                        CircularProgressIndicator(color = Color.White)
                    }
                    is SavedPicState.Success->{
                        Text("Saved Offline")
                    }
                    is SavedPicState.Error->{
                        Text("Retry")
                    }
                    else->{
                        Text("Save")
                    }
                }


            }
        },
        containerColor = WarmIvory
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 1. Premium Image Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(File(imagePath)),
                    contentDescription = "Selected journal image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Styled Date & Time Badge Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Date Badge
                Surface(
                    color = SoftSage,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)) {
                        Text("DATE", style = MaterialTheme.typography.labelSmall, color = SageGreen.copy(alpha = 0.7f))
                        Text(currentDate, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = Charcoal)
                    }
                }

                // Time Badge
                Surface(
                    color = Color(0xFFFDF0EC), // Light Terracotta tint for contrast
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)) {
                        Text("TIME", style = MaterialTheme.typography.labelSmall, color = TerracottaOrange.copy(alpha = 0.7f))
                        Text(currentTime, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold), color = Charcoal)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // MOOD
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "CURRENT MOOD",
                    style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp),
                    color = Charcoal.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.height(12.dp))

                moodOptions.chunked(4).forEach { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { moodPair ->
                            val (sticker, label) = moodPair
                            val isSelected = selectedMood == sticker

                            Surface(
                                onClick = { selectedMood = sticker },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                color = if (isSelected) SageGreen else Color.White.copy(alpha = 0.6f),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = if (isSelected) Color.Transparent else Charcoal.copy(alpha = 0.1f)
                                ),
                                tonalElevation = if (isSelected) 2.dp else 0.dp
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = sticker, style = MaterialTheme.typography.titleLarge)
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isSelected) Color.White else Charcoal.copy(alpha = 0.7f)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Immersive Note Text Input
            TextField(
                value = note,
                onValueChange = { note = it },
                placeholder = {
                    Text(
                        "What's on your mind today?...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Charcoal),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .background(Color.Transparent),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent, // Completely eliminates boxed/lined aesthetic
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
    }
}
