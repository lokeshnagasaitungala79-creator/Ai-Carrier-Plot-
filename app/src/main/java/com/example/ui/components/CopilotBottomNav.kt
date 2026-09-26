package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.AltRoute
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MainTab
import com.example.ui.theme.*

data class NavTabItem(
    val tab: MainTab,
    val label: String,
    val icon: ImageVector,
    val activeIcon: ImageVector,
    val testTag: String
)

@Composable
fun CopilotBottomNav(
    selectedTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavTabItem(MainTab.DASHBOARD, "Dashboard", Icons.Outlined.GridView, Icons.Filled.GridView, "nav_dashboard"),
        NavTabItem(MainTab.ROADMAP, "Roadmap", Icons.AutoMirrored.Outlined.AltRoute, Icons.Filled.AltRoute, "nav_roadmap"),
        NavTabItem(MainTab.DSA, "DSA", Icons.Outlined.Code, Icons.Filled.Code, "nav_dsa"),
        NavTabItem(MainTab.PROFILE, "Profile", Icons.Outlined.AccountCircle, Icons.Filled.AccountCircle, "nav_profile"),
        NavTabItem(MainTab.MORE, "More", Icons.Outlined.Apps, Icons.Filled.Apps, "nav_more")
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = CopilotSurfaceContainerLowest.copy(alpha = 0.98f),
        tonalElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, CopilotSurfaceContainerHighest.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = item.tab == selectedTab
                val tint = if (isSelected) CopilotPrimary else CopilotOutline

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = ripple(bounded = false, radius = 28.dp),
                            onClick = { onTabSelected(item.tab) }
                        )
                        .testTag(item.testTag),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = if (isSelected) item.activeIcon else item.icon,
                        contentDescription = item.label,
                        tint = tint,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = item.label,
                        color = tint,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    }
}
