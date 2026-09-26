package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.UserProfileEntity
import com.example.ui.CopilotViewModel
import com.example.ui.MainTab
import com.example.ui.components.AddEvidenceSkillDialog
import com.example.ui.components.CareerReportSheet
import com.example.ui.components.CopilotBottomNav
import com.example.ui.components.EditProfileDialog
import com.example.ui.components.EvidenceSkillDossierDialog
import com.example.ui.components.ResumeUploadDialog
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.DSAScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.MoreScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RoadmapScreen
import com.example.ui.theme.AICareerCopilotTheme
import com.example.ui.theme.CopilotOnTertiary
import com.example.ui.theme.CopilotSurface
import com.example.ui.theme.CopilotTertiary

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AICareerCopilotTheme {
                CopilotApp()
            }
        }
    }
}

@Composable
fun CopilotApp(
    viewModel: CopilotViewModel = viewModel()
) {
    val authState by viewModel.authUiState.collectAsState()
    val currentTab by viewModel.currentTab.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val showEditProfile by viewModel.showEditProfileDialog.collectAsState()
    val showResumeUpload by viewModel.showResumeUploadDialog.collectAsState()
    val showCareerReport by viewModel.showCareerReportSheet.collectAsState()
    val showAddEvidenceSkill by viewModel.showAddEvidenceSkillDialog.collectAsState()
    val selectedEvidenceSkill by viewModel.selectedEvidenceSkillForDossier.collectAsState()
    val notificationMessage by viewModel.activeNotificationMessage.collectAsState()

    val profile = userProfile ?: UserProfileEntity()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CopilotSurface)
    ) {
        if (!authState.isAuthenticated) {
            AuthScreen(viewModel = viewModel)
        } else {
            // Main App Scaffold
            BackHandler(enabled = currentTab != MainTab.DASHBOARD) {
                viewModel.selectTab(MainTab.DASHBOARD)
            }

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = CopilotSurface,
                bottomBar = {
                    CopilotBottomNav(
                        selectedTab = currentTab,
                        onTabSelected = { viewModel.selectTab(it) }
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Crossfade(
                        targetState = currentTab,
                        label = "ScreenTransition"
                    ) { tab ->
                        when (tab) {
                            MainTab.DASHBOARD -> DashboardScreen(
                                viewModel = viewModel,
                                onNavigateToProfile = { viewModel.selectTab(MainTab.PROFILE) }
                            )
                            MainTab.ROADMAP -> RoadmapScreen(viewModel = viewModel)
                            MainTab.DSA -> DSAScreen(viewModel = viewModel)
                            MainTab.PROFILE -> ProfileScreen(viewModel = viewModel)
                            MainTab.MORE -> MoreScreen(viewModel = viewModel)
                        }
                    }

                    // In-app Notification Banner
                    if (notificationMessage != null) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .align(Alignment.TopCenter),
                            shape = RoundedCornerShape(10.dp),
                            color = CopilotTertiary,
                            shadowElevation = 8.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = CopilotOnTertiary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = notificationMessage!!,
                                    color = CopilotOnTertiary,
                                    fontSize = 12.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = { viewModel.dismissNotification() },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Dismiss",
                                        tint = CopilotOnTertiary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Dialogs
            if (showEditProfile) {
                EditProfileDialog(
                    profile = profile,
                    onDismiss = { viewModel.closeEditProfileDialog() },
                    onSave = { name, role, obj, hours, gpa, inst ->
                        viewModel.saveProfile(name, role, obj, hours, gpa, inst)
                    }
                )
            }

            if (showResumeUpload) {
                ResumeUploadDialog(
                    onDismiss = { viewModel.closeResumeUploadDialog() },
                    onUploadSuccess = { file ->
                        viewModel.simulateResumeScan(file)
                    }
                )
            }

            if (showCareerReport) {
                CareerReportSheet(
                    profile = profile,
                    onDismiss = { viewModel.closeCareerReport() }
                )
            }

            if (showAddEvidenceSkill) {
                AddEvidenceSkillDialog(
                    onDismiss = { viewModel.closeAddEvidenceSkillDialog() },
                    onSave = { name, category, prof, type, title, desc, ref, metrics ->
                        viewModel.addEvidenceSkill(name, category, prof, type, title, desc, ref, metrics)
                    }
                )
            }

            if (selectedEvidenceSkill != null) {
                EvidenceSkillDossierDialog(
                    skill = selectedEvidenceSkill!!,
                    onDismiss = { viewModel.closeEvidenceSkillDossier() },
                    onDelete = { viewModel.deleteEvidenceSkill(selectedEvidenceSkill!!) }
                )
            }
        }
    }
}
