package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "Lokesh Naga Sai Tungala",
    val email: String = "lokeshnagasaitungala79@gmail.com",
    val status: String = "Active",
    val educationLevel: String = "College Senior • Class of 2025",
    val institution: String = "Tech Institute of Technology",
    val degree: String = "B.Tech Computer Science & AI",
    val gpa: String = "GPA 3.8 / 4.0",
    val targetRole: String = "Machine Learning Engineer",
    val roleMatchPercentage: Int = 94,
    val primaryObjective: String = "Securing an ML Engineer or Applied AI internship / new grad role at top tech firm by Spring 2025.",
    val dailyStudyGoalHours: Int = 2,
    val experienceLevel: String = "Beginner / Intermediate",
    val studyStreakDays: Int = 14,
    val overallReadiness: Int = 72,
    val coreSkillsMatch: Int = 78,
    val resumeCalibration: Int = 82,
    val dsaAlgorithmicRigor: Int = 55,
    val productionProjects: Int = 65,
    val atsScore: Int = 82,
    val dsaSolved: Int = 48,
    val dsaTarget: Int = 150,
    val mockAverage: Int = 76,
    val mockSessionsCount: Int = 3,
    val projectsShippedCount: Int = 3
)

@Entity(tableName = "study_tasks")
data class StudyTaskEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val category: String, // e.g. "ML Foundations", "Algorithms", "Mock Drill", "Resume Polish"
    val durationText: String, // "30m"
    val xpText: String, // "+50 XP"
    val status: String, // "COMPLETED", "IN_PROGRESS", "PENDING"
    val isDone: Boolean = false
)

@Entity(tableName = "skill_vectors")
data class SkillVectorEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val percentage: Int,
    val category: String = "Tech"
)

@Entity(tableName = "skills_matrix")
data class SkillItemEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val statusGroup: String // "VERIFIED_MASTERY", "IN_PROGRESS", "PLANNED"
)

@Entity(tableName = "evidence_skills")
data class EvidenceSkillEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: String, // "AI & Deep Learning", "ML Systems & MLOps", "Algorithms & DSA", "Cloud & Backend", "Data Engineering"
    val proficiencyPercentage: Int, // e.g. 96
    val verificationStatus: String = "VERIFIED_PROOF", // "VERIFIED_PROOF", "CODE_AUDITED", "BENCHMARK_VALIDATED", "ASSESSMENT_PASSED"
    val evidenceType: String, // "GitHub Repository", "Production Metric", "Assessment Drill", "System Architecture", "Benchmark"
    val evidenceTitle: String,
    val evidenceDescription: String,
    val artifactUrlOrRef: String = "github.com/lokeshnagasaitungala",
    val verifiedDate: String = "Verified Sept 2026",
    val metricsPill: String = "4.2K LOC • 38ms P99"
)

