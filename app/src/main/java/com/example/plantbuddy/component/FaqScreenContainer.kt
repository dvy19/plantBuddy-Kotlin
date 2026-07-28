package com.example.plantbuddy.component

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

// Green & White Color Palette
val GreenPrimary = Color(0xFF1B4D3E)       // Deep Forest Green
val GreenSecondary = Color(0xFF2E7D32)     // Medium Accent Green
val GreenContainer = Color(0xFFE8F5E9)     // Soft Green Tint for subtle highlights
val GreenSurfaceVariant = Color(0xFFF1F8F5)// Ultra-soft background tint
val OnGreenPrimary = Color(0xFFFFFFFF)     // Crisp White Text
val TextPrimary = Color(0xFF1A1C1E)        // Charcoal for high contrast readability
val TextSecondary = Color(0xFF42474E)      // Muted slate gray for body text
val SurfaceWhite = Color(0xFFFFFFFF)

data class FaqItem(
    val id: Int,
    val question: String

)

// Call from your Composable tree:
// FaqScreenContainer(faqList = sampleFaqs)

@Composable
fun FaqScreenContainer(
    faqList: List<FaqItem>,
    modifier: Modifier = Modifier,
    onFaqClick: (FaqItem) -> Unit
) {
    // Tracks currently expanded card ID (-1 means none open)
    var expandedFaqId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = modifier
            .height(200.dp)
            .background(Color(0xFFF8FAF9))
    ) {


        // Scrollable List of FAQ Cards
        LazyColumn(
            modifier = Modifier
                .height(150.dp)
                .padding(horizontal = 12.dp),
            contentPadding = PaddingValues(top = 20.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(faqList, key = { it.id }) { faq ->
                val isExpanded = faq.id == expandedFaqId
                FaqCard(
                    faq = faq,
                    isExpanded = isExpanded,
                    onToggleExpand = {
                        expandedFaqId = if (isExpanded) null else faq.id
                    }
                )
            }
        }
    }
}

@Composable
fun FaqCard(
    faq: FaqItem,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Arrow rotation transition
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 250, easing = LinearOutSlowInEasing),
        label = "ArrowRotation"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isExpanded) GreenSurfaceVariant else SurfaceWhite
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (isExpanded) GreenSecondary.copy(alpha = 0.4f) else Color(0xFFE0E0E0)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isExpanded) 2.dp else 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null // Clean tap ripple without distraction
                ) { onToggleExpand() }
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = faq.question,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = if (isExpanded) FontWeight.Bold else FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = if (isExpanded) GreenPrimary else TextPrimary
                    ),
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = onToggleExpand,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = if (isExpanded) GreenPrimary else TextSecondary,
                        modifier = Modifier.rotate(rotationState)
                    )
                }
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(
                    thickness = 1.dp,
                    color = GreenSecondary.copy(alpha = 0.15f)
                )
                Spacer(modifier = Modifier.height(12.dp))

            }
        }
    }
}

