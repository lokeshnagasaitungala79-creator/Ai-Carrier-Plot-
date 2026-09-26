package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.EvidenceSkillEntity
import com.example.data.model.SkillItemEntity
import com.example.data.model.SkillVectorEntity
import com.example.data.model.StudyTaskEntity
import com.example.data.model.UserProfileEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UserProfileEntity::class,
        StudyTaskEntity::class,
        SkillVectorEntity::class,
        SkillItemEntity::class,
        EvidenceSkillEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun copilotDao(): CopilotDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "copilot_db"
                ).fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Seed default telemetry & profile
                        CoroutineScope(Dispatchers.IO).launch {
                            val dao = getDatabase(context).copilotDao()
                            seedDatabase(dao)
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun seedDatabase(dao: CopilotDao) {
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

            dao.insertStudyTasks(
                listOf(
                    StudyTaskEntity(
                        id = 1,
                        title = "Review Random Forest & Ensemble Methods",
                        category = "ML Foundations",
                        durationText = "30m",
                        xpText = "+50 XP",
                        status = "COMPLETED",
                        isDone = true
                    ),
                    StudyTaskEntity(
                        id = 2,
                        title = "Solve 2 Binary Search Medium Problems",
                        category = "Algorithms",
                        durationText = "30m",
                        xpText = "In Progress",
                        status = "IN_PROGRESS",
                        isDone = false
                    ),
                    StudyTaskEntity(
                        id = 3,
                        title = "Practice 5 ML Technical Interview Questions",
                        category = "Mock Drill",
                        durationText = "30m",
                        xpText = "+40 XP",
                        status = "PENDING",
                        isDone = false
                    ),
                    StudyTaskEntity(
                        id = 4,
                        title = "Optimize ResNet project description on Resume",
                        category = "Resume Polish",
                        durationText = "30m",
                        xpText = "+30 XP",
                        status = "PENDING",
                        isDone = false
                    )
                )
            )

            dao.insertSkillVectors(
                listOf(
                    SkillVectorEntity(1, "Python Core & Typing", 90),
                    SkillVectorEntity(2, "SQL & Query Optimization", 75),
                    SkillVectorEntity(3, "Machine Learning (Classical)", 70),
                    SkillVectorEntity(4, "DSA (Data Structures & Algos)", 55),
                    SkillVectorEntity(5, "Deep Learning & PyTorch", 50),
                    SkillVectorEntity(6, "MLOps, CI/CD & Docker", 35),
                    SkillVectorEntity(7, "Cloud Architecture (AWS Sagemaker)", 25)
                )
            )

            dao.insertSkillsMatrix(
                listOf(
                    SkillItemEntity(1, "Python (Advanced)", "VERIFIED_MASTERY"),
                    SkillItemEntity(2, "NumPy", "VERIFIED_MASTERY"),
                    SkillItemEntity(3, "Pandas", "VERIFIED_MASTERY"),
                    SkillItemEntity(4, "Scikit-learn", "VERIFIED_MASTERY"),
                    SkillItemEntity(5, "SQL", "VERIFIED_MASTERY"),
                    SkillItemEntity(6, "PyTorch", "IN_PROGRESS"),
                    SkillItemEntity(7, "Docker", "IN_PROGRESS"),
                    SkillItemEntity(8, "Binary Search (DSA)", "IN_PROGRESS"),
                    SkillItemEntity(9, "FastAPI", "IN_PROGRESS"),
                    SkillItemEntity(10, "Kubernetes", "PLANNED"),
                    SkillItemEntity(11, "MLflow", "PLANNED"),
                    SkillItemEntity(12, "AWS SageMaker", "PLANNED")
                )
            )

            dao.insertEvidenceSkills(
                listOf(
                    EvidenceSkillEntity(
                        id = 1,
                        name = "Deep Learning & Transformers",
                        category = "AI & Deep Learning",
                        proficiencyPercentage = 96,
                        verificationStatus = "CODE_AUDITED",
                        evidenceType = "GitHub Repository",
                        evidenceTitle = "Custom Transformer from Scratch + FlashAttention v2",
                        evidenceDescription = "Implemented multi-head causal self-attention, rotary embeddings (RoPE), and KV-caching with PyTorch and Triton. Audited with 100% test coverage.",
                        artifactUrlOrRef = "github.com/lokeshnagasaitungala/transformer-core",
                        verifiedDate = "Verified Sept 2026",
                        metricsPill = "3.8K LOC • 38ms P99"
                    ),
                    EvidenceSkillEntity(
                        id = 2,
                        name = "Distributed ML & Triton Serving",
                        category = "ML Systems & MLOps",
                        proficiencyPercentage = 92,
                        verificationStatus = "BENCHMARK_VALIDATED",
                        evidenceType = "Production Metric",
                        evidenceTitle = "Ray Distributed Pipeline & Model Serving",
                        evidenceDescription = "Architected distributed embedding pipeline over 4 GPU worker nodes with Triton Inference Server, processing 12,000 req/sec with auto-batching.",
                        artifactUrlOrRef = "telemetry/ray-cluster-v2",
                        verifiedDate = "Verified Aug 2026",
                        metricsPill = "12K req/s • 99.9% SLO"
                    ),
                    EvidenceSkillEntity(
                        id = 3,
                        name = "Advanced DSA & Graph Theory",
                        category = "Algorithms & DSA",
                        proficiencyPercentage = 94,
                        verificationStatus = "ASSESSMENT_PASSED",
                        evidenceType = "Assessment Drill",
                        evidenceTitle = "LeetCode Hard Graph & DP Mastery",
                        evidenceDescription = "Completed 180+ problems in Graphs, Segment Trees, and Dynamic Programming with Top 5% time/space complexity rankings.",
                        artifactUrlOrRef = "leetcode.com/lokeshnagasai",
                        verifiedDate = "Verified Sept 2026",
                        metricsPill = "180 Solved • Top 5%"
                    ),
                    EvidenceSkillEntity(
                        id = 4,
                        name = "Vector Databases & Hybrid RAG",
                        category = "AI & Deep Learning",
                        proficiencyPercentage = 90,
                        verificationStatus = "VERIFIED_PROOF",
                        evidenceType = "System Architecture",
                        evidenceTitle = "Hierarchical Hybrid RAG Engine",
                        evidenceDescription = "Engineered hybrid dense + sparse retrieval (BM25 + BGE-large) with reciprocal rank fusion (RRF) and Milvus vector indexing.",
                        artifactUrlOrRef = "github.com/lokeshnagasaitungala/rag-engine",
                        verifiedDate = "Verified July 2026",
                        metricsPill = "94.2% Hit Rate • 60ms P95"
                    ),
                    EvidenceSkillEntity(
                        id = 5,
                        name = "Production APIs & Microservices",
                        category = "Cloud & Backend",
                        proficiencyPercentage = 88,
                        verificationStatus = "CODE_AUDITED",
                        evidenceType = "Production Project",
                        evidenceTitle = "Async FastAPI & Dockerized Edge Deployments",
                        evidenceDescription = "Dockerized asynchronous microservices with Redis caching, token rate-limiting, and OpenTelemetry distributed tracing.",
                        artifactUrlOrRef = "github.com/lokeshnagasaitungala/edge-microservices",
                        verifiedDate = "Verified June 2026",
                        metricsPill = "50K daily calls • Docker"
                    )
                )
            )
        }
    }
}
