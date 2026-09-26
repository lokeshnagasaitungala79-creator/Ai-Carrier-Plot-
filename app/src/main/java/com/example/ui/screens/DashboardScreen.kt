package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.StudyTaskEntity
import com.example.data.model.UserProfileEntity
import com.example.ui.CopilotViewModel
import com.example.ui.components.EvidenceSkillsDashboardCard
import com.example.ui.theme.*

@Composable
fun DashboardScreen(
    viewModel: CopilotViewModel,
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.userProfile.collectAsState()
    val tasks by viewModel.studyTasks.collectAsState()
    val skillVectors by viewModel.skillVectors.collectAsState()
    val evidenceSkills by viewModel.evidenceSkills.collectAsState()
    val scrollState = rememberScrollState()

    val currentProfile = profile ?: UserProfileEntity()
    val completedTasksCount = tasks.count { it.isDone }
    val totalTasksCount = if (tasks.isEmpty()) 4 else tasks.size

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CopilotSurface)
            .statusBarsPadding()
            .verticalScroll(scrollState)
            .padding(bottom = 90.dp) // extra padding for bottom navigation bar
    ) {
        // Top Header
        DashboardTopBar(
            onAvatarClick = onNavigateToProfile,
            onNotificationClick = { /* show notifications */ }
        )

        // Connected Session Live Banner
        ConnectedSessionBanner(profile = currentProfile)

        Spacer(modifier = Modifier.height(14.dp))

        // Target Role Track Hero Card
        TargetRoleTrackCard(
            profile = currentProfile,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // AI Copilot Recommendation Card
        RecommendationCard(
            onStartClick = { viewModel.markRecommendationStarted() },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Today's AI Study Plan
        TodayStudyPlanCard(
            tasks = tasks,
            completedCount = completedTasksCount,
            totalCount = totalTasksCount,
            onToggleTask = { viewModel.toggleTaskCompletion(it) },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Skill Readiness Breakdown
        SkillReadinessCard(
            skillVectors = skillVectors,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Evidence-Based Competence Signals
        EvidenceSkillsDashboardCard(
            skills = evidenceSkills,
            onExploreAllClick = onNavigateToProfile,
            onSkillClick = { viewModel.openEvidenceSkillDossier(it) },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2x2 Telemetry Metric Cards
        TelemetryGrid(
            profile = currentProfile,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DashboardTopBar(
    onAvatarClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CopilotSurfaceContainerHigh)
                    .border(1.dp, CopilotPrimary.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "https://lh3.googleusercontent.com/aida/AEtjO1XZkIvsuaNr8GBbnkuy7q_aVq455Nq28NLkiDZrQFCjjPHLlFHC4h0YNw-4a8jD6WjVEEu8_PKWH6b9yzMkldEHMmZ0mlefpvhCmLWMNTpRVNPQUFJoUDwB3tniizoPer7En9EAeWtxajsMnI38IPUfdbbVmzWYtYEPQulQPAB8SxVH_clIBD7JQ9RVvJIf4LHdtigNUCOhevI3vc-9DLW7aRBi2nmXcR7oAObf0AoDkRjHdH7BHawPfb0v",
                    contentDescription = "Logo",
                    modifier = Modifier.size(24.dp),
                    placeholder = painterResource(id = R.drawable.ic_copilot_delta),
                    error = painterResource(id = R.drawable.ic_copilot_delta)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "AI Career Copilot",
                        color = CopilotOnSurface,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        color = CopilotCyan.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "DEMO",
                            color = CopilotCyan,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                        )
                    }
                }
                Text(
                    text = "Dashboard",
                    color = CopilotOnSurfaceVariant,
                    fontSize = 11.sp
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable { onNotificationClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = CopilotOnSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
                // Dot
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = (-6).dp, y = 6.dp)
                        .clip(CircleShape)
                        .background(CopilotTertiary)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // User Avatar
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, CopilotPrimary.copy(alpha = 0.5f), CircleShape)
                    .clickable { onAvatarClick() }
            ) {
                AsyncImage(
                    model = R.drawable.avatar_alex,
                    contentDescription = "User Avatar",
                    modifier = Modifier.fillMaxSize(),
                    placeholder = painterResource(id = R.drawable.avatar_alex),
                    error = painterResource(id = R.drawable.avatar_alex)
                )
            }
        }
    }
}

@Composable
private fun ConnectedSessionBanner(profile: UserProfileEntity) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(10.dp),
        color = CopilotSurfaceContainerLowest
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(CopilotTertiary)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "CONNECTED SESSION",
                        color = CopilotTertiary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Demo User: ${profile.name} • ML Engineer Track (P...",
                        color = CopilotOnSurfaceVariant,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Surface(
                color = CopilotSurfaceContainerHigh,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "((•)) Live",
                    color = CopilotOnSurface,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun TargetRoleTrackCard(
    profile: UserProfileEntity,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, CopilotOutlineVariant.copy(alpha = 0.25f), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = CopilotSurfaceContainerLow),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = CopilotPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "TARGET ROLE TRACK",
                        color = CopilotPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                Surface(
                    color = CopilotTertiaryContainer.copy(alpha = 0.25f),
                    shape = RoundedCornerShape(50)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = CopilotTertiary,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${profile.studyStreakDays} Days",
                            color = CopilotTertiary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "STUDY STREAK",
                            color = CopilotTertiary.copy(alpha = 0.8f),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = profile.targetRole,
                color = CopilotOnSurface,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            )

            Text(
                text = "Tier 1 & Fast-Growing AI Startups Target Benchmark",
                color = CopilotOnSurfaceVariant,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Circular Readiness Meter
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Circular Progress
                    androidx.compose.foundation.Canvas(modifier = Modifier.size(120.dp)) {
                        val strokeWidth = 8.dp.toPx()
                        // Background track
                        drawCircle(
                            color = CopilotSurfaceContainerHighest,
                            style = Stroke(width = strokeWidth)
                        )
                        // Progress arc
                        val sweepAngle = (profile.overallReadiness / 100f) * 360f
                        drawArc(
                            brush = Brush.sweepGradient(
                                colors = listOf(CopilotPrimaryContainer, CopilotTertiary, CopilotCyan, CopilotPrimaryContainer)
                            ),
                            startAngle = -90f,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${profile.overallReadiness}%",
                            color = CopilotOnSurface,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "READINESS",
                            color = CopilotOnSurfaceVariant,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Track Competency Matrix
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Track Competency Matrix",
                    color = CopilotOnSurface,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Benchmark Goal: 85%",
                    color = CopilotTertiary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            CompetencyProgressBar(label = "Core Skills Match", percentage = profile.coreSkillsMatch, color = CopilotPrimary)
            CompetencyProgressBar(label = "Resume Calibration", percentage = profile.resumeCalibration, color = CopilotTertiary)
            CompetencyProgressBar(label = "DSA & Algorithmic Rigor", percentage = profile.dsaAlgorithmicRigor, color = CopilotSecondary)
            CompetencyProgressBar(label = "Production Projects", percentage = profile.productionProjects, color = CopilotPrimaryContainer)

            Spacer(modifier = Modifier.height(12.dp))

            // Footnote
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = CopilotOutline,
                    modifier = Modifier
                        .size(12.dp)
                        .offset(y = 1.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Internal progress indicator, not an employment guarantee. Calibrated against publicly available tech competencies.",
                    color = CopilotOutline,
                    fontSize = 10.sp,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

@Composable
private fun CompetencyProgressBar(
    label: String,
    percentage: Int,
    color: Color
) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                color = CopilotOnSurfaceVariant,
                fontSize = 11.sp
            )
            Text(
                text = "$percentage%",
                color = CopilotOnSurface,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { percentage / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = CopilotSurfaceContainerHighest
        )
    }
}

@Composable
private fun RecommendationCard(
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, CopilotSecondaryContainer.copy(alpha = 0.6f), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = CopilotSurfaceContainerLow),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(CopilotSecondaryContainer.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = CopilotSecondary,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "AI COPILOT RECOMMENDATION",
                        color = CopilotOnSurfaceVariant,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                Surface(
                    color = CopilotSecondaryContainer,
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "Priority #1",
                        color = CopilotOnSecondaryContainer,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Complete Binary Search & solve 3 medium problems",
                color = CopilotOnSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Binary Search is currently one of your top gap areas for ML & systems engineering interviews. Closing this brings your DSA score to 62%.",
                color = CopilotOnSurfaceVariant,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = CopilotTertiary,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "+120 XP towards Target Profile",
                        color = CopilotOnSurface,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Text(
                    text = "Est. 45 mins",
                    color = CopilotOnSurfaceVariant,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onStartClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("start_recommendation_btn"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CopilotPrimaryContainer,
                    contentColor = CopilotOnPrimaryContainer
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Start Now",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun TodayStudyPlanCard(
    tasks: List<StudyTaskEntity>,
    completedCount: Int,
    totalCount: Int,
    onToggleTask: (StudyTaskEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, CopilotOutlineVariant.copy(alpha = 0.25f), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = CopilotSurfaceContainerLow),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint = CopilotTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Today's AI Study Plan",
                        color = CopilotOnSurface,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$completedCount / $totalCount Complete",
                        color = CopilotTertiary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    LinearProgressIndicator(
                        progress = { if (totalCount > 0) completedCount / totalCount.toFloat() else 0f },
                        modifier = Modifier
                            .width(40.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = CopilotTertiary,
                        trackColor = CopilotSurfaceContainerHighest
                    )
                }
            }

            Text(
                text = "Daily Goal: 2 Hours Focused Sprint",
                color = CopilotOnSurfaceVariant,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
            )

            // Tasks List
            tasks.forEach { task ->
                StudyTaskRow(task = task, onToggle = { onToggleTask(task) })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun StudyTaskRow(
    task: StudyTaskEntity,
    onToggle: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                if (task.isDone) CopilotTertiary.copy(alpha = 0.3f) else CopilotSurfaceContainerHighest,
                RoundedCornerShape(10.dp)
            )
            .clickable { onToggle() },
        color = CopilotSurfaceContainer
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isDone,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(
                    checkedColor = CopilotTertiary,
                    uncheckedColor = CopilotOutline,
                    checkmarkColor = CopilotOnTertiary
                ),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${task.durationText} · ${task.category}",
                        color = if (task.isDone) CopilotTertiary else CopilotOnSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Surface(
                        color = if (task.isDone) CopilotTertiaryContainer.copy(alpha = 0.3f)
                        else if (task.status == "IN_PROGRESS") CopilotSurfaceContainerHighest
                        else CopilotTertiaryContainer.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = if (task.isDone) task.xpText else if (task.status == "IN_PROGRESS") "In Progress" else task.xpText,
                            color = if (task.isDone) CopilotTertiary
                            else if (task.status == "IN_PROGRESS") CopilotOnSurface
                            else CopilotTertiary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = task.title,
                    color = CopilotOnSurface,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun SkillReadinessCard(
    skillVectors: List<com.example.data.model.SkillVectorEntity>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, CopilotOutlineVariant.copy(alpha = 0.25f), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = CopilotSurfaceContainerLow),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        tint = CopilotPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Skill Readiness Breakdown",
                        color = CopilotOnSurface,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "${skillVectors.size} Skill Vectors",
                    color = CopilotOnSurfaceVariant,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            skillVectors.forEach { vector ->
                VectorProgressRow(name = vector.name, percentage = vector.percentage)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun VectorProgressRow(name: String, percentage: Int) {
    val barColor = when {
        percentage >= 75 -> CopilotTertiary
        percentage >= 50 -> CopilotCyan
        else -> CopilotSecondary
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(barColor)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = name,
                    color = CopilotOnSurface,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Text(
                text = "$percentage%",
                color = CopilotOnSurface,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        LinearProgressIndicator(
            progress = { percentage / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = barColor,
            trackColor = CopilotSurfaceContainerHighest
        )
    }
}

@Composable
private fun TelemetryGrid(
    profile: UserProfileEntity,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // ATS Score
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "ATS SCORE",
                value = "${profile.atsScore}",
                denominator = "/100",
                icon = Icons.Outlined.DocumentScanner,
                trend = "↑ +4 pts this week",
                trendColor = CopilotTertiary
            )

            // DSA Solved
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "DSA SOLVED",
                value = "${profile.dsaSolved}",
                denominator = "/${profile.dsaTarget}",
                icon = Icons.Outlined.Code,
                trend = "↗ Target: 75 for stage 2",
                trendColor = CopilotSecondary
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Mock Avg
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "MOCK AVG",
                value = "${profile.mockAverage}%",
                denominator = null,
                icon = Icons.Outlined.ChatBubbleOutline,
                trend = "✓ ${profile.mockSessionsCount} sessions recorded",
                trendColor = CopilotOnSurfaceVariant
            )

            // Projects
            MetricCard(
                modifier = Modifier.weight(1f),
                title = "PROJECTS",
                value = "${profile.projectsShippedCount}",
                denominator = "Shipped",
                icon = Icons.Outlined.RocketLaunch,
                trend = "<> ResNet, LLM, RecSys",
                trendColor = CopilotPrimary
            )
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    denominator: String?,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    trend: String,
    trendColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(1.dp, CopilotOutlineVariant.copy(alpha = 0.25f), RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(containerColor = CopilotSurfaceContainerLow),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = CopilotOutline,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = CopilotOutline,
                    modifier = Modifier.size(14.dp)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    color = CopilotOnSurface,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                if (denominator != null) {
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = denominator,
                        color = CopilotOnSurfaceVariant,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.offset(y = (-3).dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = trend,
                color = trendColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
