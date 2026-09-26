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

data class RoadmapPhase(
    val phaseNumber: Int,
    val title: String,
    val status: String, // "COMPLETED", "IN_PROGRESS", "LOCKED"
    val progressPercent: Int,
    val modules: List<RoadmapModule>
)

data class RoadmapModule(
    val title: String,
    val description: String,
    val isComplete: Boolean,
    val xpText: String
)

@Composable
fun RoadmapScreen(
    viewModel: CopilotViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val phases = listOf(
        RoadmapPhase(
            phaseNumber = 1,
            title = "Math & Machine Learning Foundations",
            status = "COMPLETED",
            progressPercent = 100,
            modules = listOf(
                RoadmapModule("Linear Algebra & Matrix Calculus", "Eigenvalues, SVD, Gradient Descent proofs", true, "+100 XP"),
                RoadmapModule("Classical ML Algorithms", "Random Forests, SVMs, Boosting, PCA", true, "+150 XP"),
                RoadmapModule("NumPy & Pandas Vectorization", "Memory efficient matrix operations", true, "+80 XP")
            )
        ),
        RoadmapPhase(
            phaseNumber = 2,
            title = "Algorithmic Rigor & Systems DSA",
            status = "IN_PROGRESS",
            progressPercent = 65,
            modules = listOf(
                RoadmapModule("Binary Search & Two Pointers", "Rotated search, boundary convergence patterns", true, "+120 XP"),
                RoadmapModule("Graph Algorithms & BFS/DFS", "Topological sort, shortest path, dependency graphs", false, "+140 XP"),
                RoadmapModule("Dynamic Programming for Optimization", "State compression, memoization trade-offs", false, "+180 XP")
            )
        ),
        RoadmapPhase(
            phaseNumber = 3,
            title = "Deep Learning & Production MLOps",
            status = "LOCKED",
            progressPercent = 25,
            modules = listOf(
                RoadmapModule("PyTorch Architecture & Custom Layers", "Backpropagation autograd, CUDA profiling", false, "+200 XP"),
                RoadmapModule("Docker & FastAPI Model Serving", "Low-latency inferencing endpoints", false, "+160 XP"),
                RoadmapModule("Distributed Training & Ray / vLLM", "Data parallelism and pipeline parallelism", false, "+250 XP")
            )
        ),
        RoadmapPhase(
            phaseNumber = 4,
            title = "FAANG System Design & Behavioral Drills",
            status = "LOCKED",
            progressPercent = 0,
            modules = listOf(
                RoadmapModule("Large Scale RecSys Architecture", "Two-tower models, approximate nearest neighbors", false, "+300 XP"),
                RoadmapModule("Voice Behavioral AI Simulation", "STAR method calibration with AI Interviewer", false, "+200 XP")
            )
        )
    )

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Dynamic Adaptive Roadmap",
                    color = CopilotOnSurface,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Machine Learning Engineer Track • Kinetic Path",
                    color = CopilotOnSurfaceVariant,
                    fontSize = 12.sp
                )
            }

            Surface(
                color = CopilotSecondaryContainer,
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = "Phase 2 Active",
                    color = CopilotOnSecondaryContainer,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Summary Metric Banner
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = CopilotSurfaceContainerLow,
            border = androidx.compose.foundation.BorderStroke(1.dp, CopilotOutlineVariant.copy(alpha = 0.25f))
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "OVERALL TRACK PROGRESS",
                        color = CopilotOutline,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "11 of 18 Milestones Cleared",
                        color = CopilotOnSurface,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Surface(
                    color = CopilotTertiaryContainer.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "61% Complete",
                        color = CopilotTertiary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Phases Timeline
        phases.forEachIndexed { index, phase ->
            RoadmapPhaseCard(phase = phase, isLast = index == phases.lastIndex)
            if (index < phases.lastIndex) {
                Spacer(modifier = Modifier.height(14.dp))
            }
        }
    }
}

@Composable
private fun RoadmapPhaseCard(phase: RoadmapPhase, isLast: Boolean) {
    val phaseColor = when (phase.status) {
        "COMPLETED" -> CopilotTertiary
        "IN_PROGRESS" -> CopilotSecondary
        else -> CopilotOutline
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (phase.status == "IN_PROGRESS") CopilotSecondary.copy(alpha = 0.5f) else CopilotOutlineVariant.copy(alpha = 0.2f),
                RoundedCornerShape(14.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = CopilotSurfaceContainerLow),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
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
                            .clip(CircleShape)
                            .background(phaseColor.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${phase.phaseNumber}",
                            color = phaseColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "PHASE ${phase.phaseNumber}",
                        color = phaseColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                Surface(
                    color = phaseColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = when (phase.status) {
                            "COMPLETED" -> "Completed"
                            "IN_PROGRESS" -> "${phase.progressPercent}% Active"
                            else -> "Locked"
                        },
                        color = phaseColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = phase.title,
                color = CopilotOnSurface,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            LinearProgressIndicator(
                progress = { phase.progressPercent / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = phaseColor,
                trackColor = CopilotSurfaceContainerHighest
            )

            Spacer(modifier = Modifier.height(12.dp))

            phase.modules.forEach { mod ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (mod.isComplete) Icons.Default.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = if (mod.isComplete) CopilotTertiary else CopilotOutline,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = mod.title,
                            color = if (mod.isComplete) CopilotOnSurface else CopilotOnSurfaceVariant,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = mod.description,
                            color = CopilotOutline,
                            fontSize = 10.sp
                        )
                    }
                    Text(
                        text = mod.xpText,
                        color = CopilotSecondary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
