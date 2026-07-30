import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.plantbuddy.auth.SessionManager
import com.example.plantbuddy.room.WaterStreakVMFactory
import com.example.plantbuddy.waterStreak.GetWaterStreakState
import com.example.plantbuddy.waterStreak.WaterLogRepo
import com.example.plantbuddy.waterStreak.WaterLogReq
import com.example.plantbuddy.waterStreak.WaterPlantState
import com.example.plantbuddy.waterStreak.WaterViewModel

@Composable
fun WaterStreakScreen(
    mainNavController: NavHostController,
    plantId: Int = 3,
) {

    val context= LocalContext.current
    val sessionManager= SessionManager(context)

    val repo= WaterLogRepo(sessionManager)


    val viewModel: WaterViewModel=viewModel(factory = WaterStreakVMFactory(repo))

    val streakState by viewModel.getWaterStreakState.collectAsStateWithLifecycle()
    val waterState by viewModel.waterPlantState.collectAsStateWithLifecycle()

    // Fetch initial streak details
    LaunchedEffect(plantId) {
        viewModel.getWaterStreak(plantId)
    }

    // Refresh calendar after successfully posting today's water log
    LaunchedEffect(waterState) {
        if (waterState is WaterPlantState.Success) {
            viewModel.getWaterStreak( plantId)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (val state = streakState) {
            is GetWaterStreakState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is GetWaterStreakState.Success -> {
                val data = state.data
                val wateredDatesSet = remember(data.watered_dates) {
                    data.watered_dates.mapNotNull {
                        try { LocalDate.parse(it) } catch (e: Exception) { null }
                    }.toSet()
                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Header: Streak Counter
                    Text(
                        text = "🔥 ${data.streak} Day Streak",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Basic Calendar Grid
                    MinimalCalendarGrid(wateredDates = wateredDatesSet)

                    // Today's Action Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (data.watered_today) Color(0xFFE8F5E9) else MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = "Water Plant Today", fontWeight = FontWeight.SemiBold)
                                Text(
                                    text = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM d, yyyy")),
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }

                            if (waterState is WaterPlantState.Loading) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                            } else {
                                Checkbox(
                                    checked = data.watered_today,
                                    onCheckedChange = { isChecked ->
                                        if (isChecked) {
                                            viewModel.waterPlant( WaterLogReq(plant = plantId))
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF4CAF50))
                                )
                            }
                        }
                    }
                }
            }

            is GetWaterStreakState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.message, color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.getWaterStreak( plantId) }) {
                        Text("Retry")
                    }
                }
            }

            GetWaterStreakState.Idle -> {}
        }
    }
}

@Composable
fun MinimalCalendarGrid(wateredDates: Set<LocalDate>) {
    val currentMonth = YearMonth.now()
    val firstDayOfMonth = currentMonth.atDay(1)
    val dayOffset = firstDayOfMonth.dayOfWeek.value % 7
    val startDate = firstDayOfMonth.minusDays(dayOffset.toLong())

    Column {
        Text(
            text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Day Headers (S, M, T, W, T, F, S)
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 35 Grid Cells (5 Weeks)
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.height(240.dp)
        ) {
            items(35) { index ->
                val date = startDate.plusDays(index.toLong())
                val isWatered = wateredDates.contains(date)
                val isCurrentMonth = date.month == currentMonth.month

                val boxColor = when {
                    isWatered -> Color(0xFF4CAF50) // Green box for watered dates
                    !isCurrentMonth -> Color.LightGray.copy(alpha = 0.2f)
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }

                val textColor = when {
                    isWatered -> Color.White
                    !isCurrentMonth -> Color.Gray
                    else -> MaterialTheme.colorScheme.onSurface
                }

                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(6.dp))
                        .background(boxColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = date.dayOfMonth.toString(),
                        fontSize = 12.sp,
                        color = textColor,
                        fontWeight = if (date == LocalDate.now()) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}