package com.example.plantbuddy.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.plantbuddy.plants.Fact
import com.example.plantbuddy.room.DatabaseProvider
import com.example.plantbuddy.room.SavedFactRepository
import com.example.plantbuddy.room.SavedFactState
import com.example.plantbuddy.room.SavedFactViewModel
import com.example.plantbuddy.room.SavedViewModelFac

// --- Greenery Color Palette Setup ---
 val EmeraldPrimary = Color(0xFF2E6F40)      // Primary Green
 val ForestDarkContainer = Color(0xFF1B4D2E) // Dark Green Container
 val MintContainer = Color(0xFFE2F3E5)       // Soft Green Light Accent
 val SageOnContainer = Color(0xFF0F381D)     // Dark Text on Light Green
 val SoftWhiteBackground = Color(0xFFFBFDFC) // Very Soft Off-White Background
val TextDark = Color(0xFF191C19)            // Primary Body Dark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactDetails(
    mainNavController: NavController,
    fact: Fact,
    onBackClick: () -> Unit = {},
    onShareClick: () -> Unit = {},
    onLikeClick: () -> Unit = {},
    onSaveOfflineClick: () -> Unit = {}
) {



    val context = LocalContext.current

    val viewModel: SavedFactViewModel = viewModel(
        factory = remember(context) {
            val database = DatabaseProvider.getDatabase(context.applicationContext)
            val repository = SavedFactRepository(database.savedFactDao())
            SavedViewModelFac(repository)
        }
    )

    val saveState by viewModel.saveState.collectAsState()



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Today's Fact",
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
                actions = {
                    IconButton(onClick = onShareClick) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share Fact",
                            tint = EmeraldPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SoftWhiteBackground
                )
            )
        },
        bottomBar = {
            // Floating or pinned bottom action row
            Surface(
                tonalElevation = 3.dp,
                shadowElevation = 8.dp,
                color = SoftWhiteBackground
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Like Button (Outlined Accent)
                    OutlinedButton(
                        onClick = onLikeClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = EmeraldPrimary
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(EmeraldPrimary)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Like",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    // Save Offline Button (Filled Primary Accent)
                    Button(
                        onClick = { viewModel.saveFact(fact) },
                        modifier = Modifier
                            .weight(1.2f)

                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EmeraldPrimary,
                            contentColor = Color.White
                        )
                    ) {

                        Spacer(modifier = Modifier.width(8.dp))
                        when (saveState) {
                            is SavedFactState.Idle -> {
                                Icon(
                                    imageVector = Icons.Default.BookmarkBorder,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)

                                )
                                Text("Save Offline")
                            }
                            is SavedFactState.Loading -> CircularProgressIndicator(color = Color.White)
                            is SavedFactState.Success -> {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)

                                )
                                Text("Saved Offline")
                            }
                            is SavedFactState.Error -> Text("Retry Save")
                            else -> Text("Save Offline")
                        }
                    }
                }
            }
        },
        containerColor = SoftWhiteBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            // Category Badge
            Surface(
                color = MintContainer,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    text = fact.category.uppercase(),
                    color = SageOnContainer,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            // Fact Title
            Text(
                text = fact.title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            HorizontalDivider(
                color = MintContainer,
                thickness = 1.dp,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Main Card/Content Area
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                tonalElevation = 1.dp,
                shadowElevation = 2.dp
            ) {
                Text(
                    text = fact.fact,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = TextDark,
                        lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.35f
                    ),
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
    }
}