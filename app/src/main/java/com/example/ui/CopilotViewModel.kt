package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.EvidenceSkillEntity
import com.example.data.model.SkillItemEntity
import com.example.data.model.SkillVectorEntity
import com.example.data.model.StudyTaskEntity
import com.example.data.model.UserProfileEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MainTab {
    DASHBOARD,
    ROADMAP,
    DSA,
    PROFILE,
    MORE
}

data class AuthUiState(
    val isAuthenticated: Boolean = false,
    val isSignUpTab: Boolean = false,
    val email: String = "lokeshnagasaitungala79@gmail.com",
    val accessKey: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val statusMessage: String? = null,
    val errorMessage: String? = null
)

class CopilotViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val dao = database.copilotDao()

    companion object {
        val AUTHORIZED_EMAILS = listOf(
            "lokeshnagasaitungala79@gmail.com",
            "lokeshnagasaiungala79@gmail.com"
        )
        const val REQUIRED_ACCESS_KEY = "Lokesh Naga Sai 143"
    }

    fun isAuthorizedEmail(inputEmail: String): Boolean {
        val clean = inputEmail.trim().lowercase()
        return AUTHORIZED_EMAILS.any { it.equals(clean, ignoreCase = true) }
    }

    fun isAuthorizedAccessKey(inputKey: String): Boolean {
        return inputKey.trim() == REQUIRED_ACCESS_KEY
    }

    init {
        // Ensure initial database seeding
        viewModelScope.launch(Dispatchers.IO) {
            val existing = database.openHelper.readableDatabase
            // Check if profile exists, seed and ensure Lokesh profile
            AppDatabase.seedDatabase(dao)
            dao.insertOrUpdateProfile(
                UserProfileEntity(
                    id = 1,
                    name = "Lokesh Naga Sai Tungala",
                    email = "lokeshnagasaitungala79@gmail.com",
                    status = "Active",
                    educationLevel = "College Senior • Class of 2025",
                    institution = "Tech Institute of Technology",
                    degree = "B.Tech Computer Science & AI",
                    gpa = "GPA 3.8 / 4.0",
                    targetRole = "Machine Learning Engineer",
                    roleMatchPercentage = 94,
                    primaryObjective = "Securing an ML Engineer or Applied AI internship / new grad role at top tech firm by Spring 2025.",
                    dailyStudyGoalHours = 2,
                    experienceLevel = "Beginner / Intermediate",
                    studyStreakDays = 14,
                    overallReadiness = 72,
                    coreSkillsMatch = 78,
                    resumeCalibration = 82,
                    dsaAlgorithmicRigor = 55,
                    productionProjects = 65,
                    atsScore = 82,
                    dsaSolved = 48,
                    dsaTarget = 150,
                    mockAverage = 76,
                    mockSessionsCount = 3,
                    projectsShippedCount = 3
                )
            )
        }
    }

    val userProfile: StateFlow<UserProfileEntity?> = dao.getUserProfile()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            UserProfileEntity()
        )

    val studyTasks: StateFlow<List<StudyTaskEntity>> = dao.getAllStudyTasks()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val skillVectors: StateFlow<List<SkillVectorEntity>> = dao.getSkillVectors()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val skillsMatrix: StateFlow<List<SkillItemEntity>> = dao.getSkillsMatrix()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val evidenceSkills: StateFlow<List<EvidenceSkillEntity>> = dao.getAllEvidenceSkills()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _currentTab = MutableStateFlow(MainTab.DASHBOARD)
    val currentTab: StateFlow<MainTab> = _currentTab.asStateFlow()

    private val _authUiState = MutableStateFlow(AuthUiState())
    val authUiState: StateFlow<AuthUiState> = _authUiState.asStateFlow()

    // Dialog & Interaction States
    private val _showEditProfileDialog = MutableStateFlow(false)
    val showEditProfileDialog: StateFlow<Boolean> = _showEditProfileDialog.asStateFlow()

    private val _showCareerReportSheet = MutableStateFlow(false)
    val showCareerReportSheet: StateFlow<Boolean> = _showCareerReportSheet.asStateFlow()

    private val _showResumeUploadDialog = MutableStateFlow(false)
    val showResumeUploadDialog: StateFlow<Boolean> = _showResumeUploadDialog.asStateFlow()

    private val _showRecommendationModal = MutableStateFlow(false)
    val showRecommendationModal: StateFlow<Boolean> = _showRecommendationModal.asStateFlow()

    private val _showAddEvidenceSkillDialog = MutableStateFlow(false)
    val showAddEvidenceSkillDialog: StateFlow<Boolean> = _showAddEvidenceSkillDialog.asStateFlow()

    private val _selectedEvidenceSkillForDossier = MutableStateFlow<EvidenceSkillEntity?>(null)
    val selectedEvidenceSkillForDossier: StateFlow<EvidenceSkillEntity?> = _selectedEvidenceSkillForDossier.asStateFlow()

    private val _activeNotificationMessage = MutableStateFlow<String?>(null)
    val activeNotificationMessage: StateFlow<String?> = _activeNotificationMessage.asStateFlow()

    fun selectTab(tab: MainTab) {
        _currentTab.value = tab
    }

    fun setAuthTab(isSignUp: Boolean) {
        _authUiState.value = _authUiState.value.copy(isSignUpTab = isSignUp)
    }

    fun updateEmail(email: String) {
        _authUiState.value = _authUiState.value.copy(
            email = email,
            errorMessage = null
        )
    }

    fun updateAccessKey(key: String) {
        _authUiState.value = _authUiState.value.copy(
            accessKey = key,
            errorMessage = null
        )
    }

    fun togglePasswordVisibility() {
        _authUiState.value = _authUiState.value.copy(
            isPasswordVisible = !_authUiState.value.isPasswordVisible
        )
    }

    fun fillDemoSandboxAndLogin() {
        _authUiState.value = _authUiState.value.copy(
            email = "lokeshnagasaitungala79@gmail.com",
            accessKey = "",
            statusMessage = null,
            errorMessage = "Authorized profile selected. Please enter your access key below to open the session."
        )
    }

    fun launchCopilotSession() {
        val currentEmail = _authUiState.value.email.trim()
        val currentKey = _authUiState.value.accessKey.trim()

        if (!isAuthorizedEmail(currentEmail)) {
            _authUiState.value = _authUiState.value.copy(
                errorMessage = "Access Restricted: Authentication is exclusively authorized for lokeshnagasaitungala79@gmail.com"
            )
            return
        }

        if (currentKey.isEmpty()) {
            _authUiState.value = _authUiState.value.copy(
                errorMessage = "Please enter your access key."
            )
            return
        }

        if (!isAuthorizedAccessKey(currentKey)) {
            _authUiState.value = _authUiState.value.copy(
                errorMessage = "Invalid Access Key: The session can only be opened with the authorized access key."
            )
            return
        }

        viewModelScope.launch {
            _authUiState.value = _authUiState.value.copy(
                isLoading = true,
                statusMessage = "Verifying Token & Calibrating Profile...",
                errorMessage = null
            )
            delay(700)
            _authUiState.value = _authUiState.value.copy(
                isLoading = false,
                isAuthenticated = true,
                statusMessage = null,
                errorMessage = null
            )
        }
    }

    fun logout() {
        _authUiState.value = _authUiState.value.copy(isAuthenticated = false)
        _currentTab.value = MainTab.DASHBOARD
    }

    fun toggleTaskCompletion(task: StudyTaskEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val newIsDone = !task.isDone
            val newStatus = if (newIsDone) "COMPLETED" else "IN_PROGRESS"
            dao.updateStudyTask(task.copy(isDone = newIsDone, status = newStatus))

            // Recalculate slightly
            val currentProfile = userProfile.value ?: UserProfileEntity()
            val newReadiness = if (newIsDone) {
                (currentProfile.overallReadiness + 1).coerceAtMost(100)
            } else {
                (currentProfile.overallReadiness - 1).coerceAtLeast(0)
            }
            dao.insertOrUpdateProfile(currentProfile.copy(overallReadiness = newReadiness))
        }
    }

    fun openEditProfileDialog() {
        _showEditProfileDialog.value = true
    }

    fun closeEditProfileDialog() {
        _showEditProfileDialog.value = false
    }

    fun saveProfile(
        name: String,
        targetRole: String,
        objective: String,
        studyHours: Int,
        gpa: String,
        institution: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val current = userProfile.value ?: UserProfileEntity()
            val updated = current.copy(
                name = name,
                targetRole = targetRole,
                primaryObjective = objective,
                dailyStudyGoalHours = studyHours,
                gpa = gpa,
                institution = institution
            )
            dao.insertOrUpdateProfile(updated)
            _showEditProfileDialog.value = false
        }
    }

    fun setDailyStudyHours(hours: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val current = userProfile.value ?: UserProfileEntity()
            dao.insertOrUpdateProfile(current.copy(dailyStudyGoalHours = hours))
        }
    }

    fun openResumeUploadDialog() {
        _showResumeUploadDialog.value = true
    }

    fun closeResumeUploadDialog() {
        _showResumeUploadDialog.value = false
    }

    fun simulateResumeScan(resumeName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val current = userProfile.value ?: UserProfileEntity()
            val updatedAts = (current.atsScore + 3).coerceAtMost(99)
            val updatedCalibration = (current.resumeCalibration + 2).coerceAtMost(98)
            dao.insertOrUpdateProfile(
                current.copy(
                    atsScore = updatedAts,
                    resumeCalibration = updatedCalibration
                )
            )
            _showResumeUploadDialog.value = false
            _activeNotificationMessage.value = "Resume '$resumeName' calibrated! ATS Score boosted to $updatedAts%."
        }
    }

    fun openCareerReport() {
        _showCareerReportSheet.value = true
    }

    fun closeCareerReport() {
        _showCareerReportSheet.value = false
    }

    fun openRecommendationModal() {
        _showRecommendationModal.value = true
    }

    fun closeRecommendationModal() {
        _showRecommendationModal.value = false
    }

    fun dismissNotification() {
        _activeNotificationMessage.value = null
    }

    fun markRecommendationStarted() {
        _showRecommendationModal.value = false
        _currentTab.value = MainTab.DSA
        _activeNotificationMessage.value = "Binary Search Drill active. Solve 3 medium problems to gain +120 XP!"
    }

    fun openAddEvidenceSkillDialog() {
        _showAddEvidenceSkillDialog.value = true
    }

    fun closeAddEvidenceSkillDialog() {
        _showAddEvidenceSkillDialog.value = false
    }

    fun openEvidenceSkillDossier(skill: EvidenceSkillEntity) {
        _selectedEvidenceSkillForDossier.value = skill
    }

    fun closeEvidenceSkillDossier() {
        _selectedEvidenceSkillForDossier.value = null
    }

    fun addEvidenceSkill(
        name: String,
        category: String,
        proficiency: Int,
        evidenceType: String,
        evidenceTitle: String,
        evidenceDescription: String,
        artifactUrlOrRef: String,
        metricsPill: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val newSkill = EvidenceSkillEntity(
                name = name,
                category = category,
                proficiencyPercentage = proficiency,
                verificationStatus = "VERIFIED_PROOF",
                evidenceType = evidenceType,
                evidenceTitle = evidenceTitle,
                evidenceDescription = evidenceDescription,
                artifactUrlOrRef = artifactUrlOrRef.ifBlank { "github.com/lokeshnagasaitungala" },
                verifiedDate = "Verified Sept 2026",
                metricsPill = metricsPill.ifBlank { "Audit Passed" }
            )
            dao.insertEvidenceSkill(newSkill)
            _showAddEvidenceSkillDialog.value = false
            _activeNotificationMessage.value = "Evidence-based skill '$name' verified and linked to dossier!"
        }
    }

    fun deleteEvidenceSkill(skill: EvidenceSkillEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteEvidenceSkill(skill)
            _selectedEvidenceSkillForDossier.value = null
            _activeNotificationMessage.value = "Skill evidence '${skill.name}' removed from dossier."
        }
    }
}
