package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.SkillItemEntity
import com.example.data.model.UserProfileEntity
import com.example.ui.CopilotViewModel
import com.example.ui.components.EvidenceSkillsHub
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    viewModel: CopilotViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.userProfile.collectAsState()
    val skills by viewModel.skillsMatrix.collectAsState()
    val evidenceSkills by viewModel.evidenceSkills.collectAsState()
    val currentProfile = profile ?: UserProfileEntity()
    val scrollState = rememberScrollState()

    var selectedTab by remember { mutableStateOf("Overview") }
    var selectedHours by remember(currentProfile.dailyStudyGoalHours) {
        mutableIntStateOf(currentProfile.dailyStudyGoalHours)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CopilotSurface)
            .statusBarsPadding()
            .verticalScroll(scrollState)
            .padding(bottom = 90.dp)
    ) {
        // Top Header
        ProfileTopBar()

        // Profile Identity Card
        ProfileIdentitySection(profile = currentProfile)

        Spacer(modifier = Modifier.height(14.dp))

        // Navigation Tabs (Overview, Skills, Security, Prefs)
        ProfileTabsRow(
            selectedTab = selectedTab,
            onSelectTab = { selectedTab = it },
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        when (selectedTab) {
            "Overview" -> {
                // Career & Study Parameters
                CareerParametersCard(
                    profile = currentProfile,
                    selectedHours = selectedHours,
                    onSelectHours = {
                        selectedHours = it
                        viewModel.setDailyStudyHours(it)
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Evidence-Based Skills Section
                EvidenceSkillsHub(
                    skills = evidenceSkills,
                    onAddClick = { viewModel.openAddEvidenceSkillDialog() },
                    onSkillClick = { viewModel.openEvidenceSkillDossier(it) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Skills Matrix
                SkillsMatrixCard(
                    skills = skills,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            "Skills" -> {
                // Full Evidence-Based Skills Hub
                EvidenceSkillsHub(
                    skills = evidenceSkills,
                    onAddClick = { viewModel.openAddEvidenceSkillDialog() },
                    onSkillClick = { viewModel.openEvidenceSkillDossier(it) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                SkillsMatrixCard(
                    skills = skills,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            "Security" -> {
                SecuritySettingsCard(
                    profile = currentProfile,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            "Prefs" -> {
                CareerParametersCard(
                    profile = currentProfile,
                    selectedHours = selectedHours,
                    onSelectHours = {
                        selectedHours = it
                        viewModel.setDailyStudyHours(it)
                    },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Actions
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Edit Profile Primary Button
            Button(
                onClick = { viewModel.openEditProfileDialog() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .testTag("edit_profile_btn"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CopilotPrimaryContainer,
                    contentColor = CopilotOnPrimaryContainer
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Edit Profile",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 2-Column Actions: Re-upload Resume & Career Report
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.openResumeUploadDialog() },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("reupload_resume_btn"),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = CopilotOnSurface,
                        containerColor = CopilotSurfaceContainerHigh
                    ),
                    border = null,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.UploadFile,
                        contentDescription = null,
                        tint = CopilotTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Re-upload Resume",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                OutlinedButton(
                    onClick = { viewModel.openCareerReport() },
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .testTag("career_report_btn"),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = CopilotOnSurface,
                        containerColor = CopilotSurfaceContainerHigh
                    ),
                    border = null,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Download,
                        contentDescription = null,
                        tint = CopilotSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Career Report",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Log Out Button
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { viewModel.logout() }
                    .testTag("logout_btn"),
                color = Color(0xFF381014),
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = null,
                        tint = CopilotError,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Log Out",
                        color = CopilotError,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun ProfileTopBar() {
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
                    text = "Profile",
                    color = CopilotOnSurfaceVariant,
                    fontSize = 11.sp
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = CopilotOnSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, CopilotPrimary.copy(alpha = 0.5f), CircleShape)
            ) {
                AsyncImage(
                    model = R.drawable.avatar_alex,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    placeholder = painterResource(id = R.drawable.avatar_alex),
                    error = painterResource(id = R.drawable.avatar_alex)
                )
            }
        }
    }
}

@Composable
private fun ProfileIdentitySection(profile: UserProfileEntity) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Large Avatar with camera overlay
            Box(
                modifier = Modifier.size(76.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .clip(CircleShape)
                        .border(2.dp, CopilotPrimary.copy(alpha = 0.6f), CircleShape)
                ) {
                    AsyncImage(
                        model = R.drawable.avatar_alex,
                        contentDescription = "Avatar",
                        modifier = Modifier.fillMaxSize(),
                        placeholder = painterResource(id = R.drawable.avatar_alex),
                        error = painterResource(id = R.drawable.avatar_alex)
                    )
                }

                // Camera icon overlay
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.BottomEnd)
                        .clip(CircleShape)
                        .background(CopilotPrimaryContainer)
                        .border(1.5.dp, CopilotSurface, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = null,
                        tint = CopilotOnPrimaryContainer,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = profile.name,
                        color = CopilotOnSurface,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = CopilotTertiaryContainer.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(50)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(CopilotTertiary)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Active",
                                color = CopilotTertiary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = profile.educationLevel,
                    color = CopilotOnSurfaceVariant,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.School,
                        contentDescription = null,
                        tint = CopilotOnSurfaceVariant,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = profile.institution,
                        color = CopilotOnSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Degree & GPA row
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = CopilotSurfaceContainerLow,
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.MilitaryTech,
                        contentDescription = null,
                        tint = CopilotTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = profile.degree,
                        color = CopilotOnSurface,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Surface(
                    color = CopilotTertiaryContainer.copy(alpha = 0.25f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = profile.gpa,
                        color = CopilotTertiary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Target Career Track Card
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = CopilotSurfaceContainerLow,
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, CopilotOutlineVariant.copy(alpha = 0.25f))
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Target Career Track",
                        color = CopilotOnSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = CopilotSecondary,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${profile.roleMatchPercentage}% Match",
                            color = CopilotSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = CopilotSurfaceContainerHigh,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 9.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(CopilotSecondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = CopilotOnSecondaryContainer,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = profile.targetRole,
                                color = CopilotOnSurface,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = CopilotOnSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileTabsRow(
    selectedTab: String,
    onSelectTab: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        Pair("Overview", Icons.Outlined.Dashboard),
        Pair("Skills", Icons.Outlined.Verified),
        Pair("Security", Icons.Outlined.Security),
        Pair("Prefs", Icons.Outlined.Tune)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(CopilotSurfaceContainerLowest, RoundedCornerShape(10.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        tabs.forEach { (title, icon) ->
            val isSelected = selectedTab == title
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onSelectTab(title) },
                color = if (isSelected) CopilotSurfaceContainerHigh else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 7.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isSelected) CopilotPrimary else CopilotOnSurfaceVariant,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = title,
                        color = if (isSelected) CopilotPrimary else CopilotOnSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Composable
private fun CareerParametersCard(
    profile: UserProfileEntity,
    selectedHours: Int,
    onSelectHours: (Int) -> Unit,
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
                        text = "Career & Study Parameters",
                        color = CopilotOnSurface,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Surface(
                    color = CopilotSurfaceContainerHighest,
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "Phase 1 Active",
                        color = CopilotOnSurfaceVariant,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Primary Objective
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Flag,
                        contentDescription = null,
                        tint = CopilotOutline,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Primary Objective",
                        color = CopilotOnSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "“${profile.primaryObjective}”",
                    color = CopilotOnSurface,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Daily Study Goal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = CopilotOutline,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Daily Study Goal",
                        color = CopilotOnSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Text(
                    text = "$selectedHours Hours / Day",
                    color = CopilotTertiary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Selector Pills (1h / day, 2h / day, 3h+ / day)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(1 to "1h / day", 2 to "2h / day", 3 to "3h+ / day").forEach { (hours, label) ->
                    val isSelected = selectedHours == hours
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onSelectHours(hours) },
                        color = if (isSelected) CopilotTertiaryContainer else CopilotSurfaceContainerHigh,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = label,
                            color = if (isSelected) CopilotTertiary else CopilotOnSurfaceVariant,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.padding(vertical = 8.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Experience Level
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.TrendingUp,
                        contentDescription = null,
                        tint = CopilotOutline,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Experience Level\nSenior Student",
                        color = CopilotOnSurfaceVariant,
                        fontSize = 11.sp,
                        lineHeight = 14.sp
                    )
                }

                Surface(
                    color = CopilotSurfaceContainerHigh,
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = profile.experienceLevel,
                        color = CopilotOnSurface,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Preferred Core Tech Stacks
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Code,
                        contentDescription = null,
                        tint = CopilotOutline,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Preferred Core Tech Stacks",
                        color = CopilotOnSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TechStackPill(dotColor = CopilotSecondary, text = "Python  (v3.11)  Core AI")
                    TechStackPill(dotColor = CopilotTertiary, text = "C++  (v20)  DSA Grind")
                }
            }
        }
    }
}

@Composable
private fun TechStackPill(dotColor: Color, text: String) {
    Surface(
        color = CopilotSurfaceContainerHigh,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(dotColor)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                color = CopilotOnSurface,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SkillsMatrixCard(
    skills: List<SkillItemEntity>,
    modifier: Modifier = Modifier
) {
    val verified = skills.filter { it.statusGroup == "VERIFIED_MASTERY" }
    val inProgress = skills.filter { it.statusGroup == "IN_PROGRESS" }
    val planned = skills.filter { it.statusGroup == "PLANNED" }

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
                        imageVector = Icons.Default.GridView,
                        contentDescription = null,
                        tint = CopilotTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Skills Matrix",
                        color = CopilotOnSurface,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "${skills.size} Total Tracked",
                    color = CopilotOnSurfaceVariant,
                    fontSize = 11.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Verified Mastery
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = CopilotTertiary,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Verified Mastery (${verified.size})",
                    color = CopilotTertiary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                verified.forEach { item ->
                    Surface(
                        color = CopilotTertiaryContainer.copy(alpha = 0.35f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = item.name,
                            color = CopilotTertiary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // In Progress • Milestone Benchmarks
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Sync,
                    contentDescription = null,
                    tint = CopilotSecondary,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "In Progress • Milestone Benchmarks (${inProgress.size})",
                    color = CopilotSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                inProgress.forEach { item ->
                    Surface(
                        color = CopilotSurfaceContainerHigh,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(CopilotOnSurfaceVariant)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = item.name,
                                color = CopilotOnSurface,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Planned Roadmaps
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null,
                    tint = CopilotOutline,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Planned Roadmaps (${planned.size})",
                    color = CopilotOnSurfaceVariant,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                planned.forEach { item ->
                    Surface(
                        color = CopilotSurfaceContainerLowest,
                        shape = RoundedCornerShape(6.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CopilotSurfaceContainerHighest)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Schedule,
                                contentDescription = null,
                                tint = CopilotOutline,
                                modifier = Modifier.size(11.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = item.name,
                                color = CopilotOnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SecuritySettingsCard(
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Security,
                    contentDescription = null,
                    tint = CopilotTertiary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Security & Verification",
                    color = CopilotOnSurface,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Academic SSO Bound: ${profile.email}",
                color = CopilotOnSurfaceVariant,
                fontSize = 12.sp
            )
            Text(
                text = "Encryption: AES-256 GCM Hardware Keystore",
                color = CopilotTertiary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "FERPA Verified: Student Telemetry Sandbox Enabled",
                color = CopilotOnSurfaceVariant,
                fontSize = 11.sp
            )
        }
    }
}
