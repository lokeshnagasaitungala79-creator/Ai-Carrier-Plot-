package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.CopilotViewModel
import com.example.ui.theme.*

@Composable
fun MoreScreen(
    viewModel: CopilotViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CopilotSurface)
            .statusBarsPadding()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .padding(bottom = 90.dp)
    ) {
        Spacer(modifier = Modifier.height(14.dp))

        // Screen Header
        Text(
            text = "AI Suite Tools & Settings",
            color = CopilotOnSurface,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Advanced Copilot Engines & Preferences",
            color = CopilotOnSurfaceVariant,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "INTELLIGENT SUITE ENGINES",
            color = CopilotOutline,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        ToolMenuCard(
            title = "AI Resume & ATS Gap Analysis",
            subtitle = "Calibrate tech keywords against FAANG Tier 1 job postings",
            badge = "98.4% Match",
            badgeColor = CopilotTertiary,
            icon = Icons.Outlined.DocumentScanner,
            onClick = { viewModel.openResumeUploadDialog() }
        )

        Spacer(modifier = Modifier.height(8.dp))

        ToolMenuCard(
            title = "Voice Mock Interview Simulator",
            subtitle = "Simulated ML behavioral and system design interview rounds",
            badge = "Live Audio",
            badgeColor = CopilotSecondary,
            icon = Icons.Outlined.Mic,
            onClick = { viewModel.openCareerReport() }
        )

        Spacer(modifier = Modifier.height(8.dp))

        ToolMenuCard(
            title = "Executive Career Growth Report",
            subtitle = "Synthesized readiness telemetry report and study roadmap",
            badge = "PDF Ready",
            badgeColor = CopilotCyan,
            icon = Icons.Outlined.Assessment,
            onClick = { viewModel.openCareerReport() }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "PREFERENCES & SYSTEM",
            color = CopilotOutline,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        SettingsRow(
            title = "Connected Campus SSO",
            subtitle = "Tech Institute of Technology • Validated",
            icon = Icons.Outlined.School
        )

        Spacer(modifier = Modifier.height(6.dp))

        SettingsRow(
            title = "Study Sprint Notification Cadence",
            subtitle = "Daily 7:00 PM CST focused reminders",
            icon = Icons.Outlined.Notifications
        )

        Spacer(modifier = Modifier.height(6.dp))

        SettingsRow(
            title = "Telemetry Data Privacy & FERPA",
            subtitle = "256-bit AES client-side encryption active",
            icon = Icons.Outlined.Shield
        )

        Spacer(modifier = Modifier.height(20.dp))

        // App Version Pill
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            color = CopilotSurfaceContainerLowest
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "AI Career Copilot • Version 1.0.4 Enterprise",
                    color = CopilotOnSurfaceVariant,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Built for Ambitious CS & ML Engineers",
                    color = CopilotOutline,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
private fun ToolMenuCard(
    title: String,
    subtitle: String,
    badge: String,
    badgeColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, CopilotOutlineVariant.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = CopilotSurfaceContainerLow),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CopilotSurfaceContainerHigh),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = badgeColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        color = CopilotOnSurface,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Surface(
                        color = badgeColor.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = badge,
                            color = badgeColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = subtitle,
                    color = CopilotOnSurfaceVariant,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = CopilotOutline,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun SettingsRow(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = CopilotSurfaceContainerLow,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = CopilotOutline,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = CopilotOnSurface,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    color = CopilotOutline,
                    fontSize = 10.sp
                )
            }
        }
    }
}
