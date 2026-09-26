package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.EvidenceSkillEntity
import com.example.ui.theme.*

/**
 * Hub displaying evidence-based skills with domain filtering,
 * proof metrics, and deep-dive verification dossiers.
 */
@Composable
fun EvidenceSkillsHub(
    skills: List<EvidenceSkillEntity>,
    onAddClick: () -> Unit,
    onSkillClick: (EvidenceSkillEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "AI & Deep Learning", "ML Systems & MLOps", "Algorithms & DSA", "Cloud & Backend")

    val filteredSkills = remember(skills, selectedCategory) {
        if (selectedCategory == "All") skills
        else skills.filter { it.category.equals(selectedCategory, ignoreCase = true) }
    }

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
            // Header with title, badge & Add Evidence button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(CopilotTertiaryContainer.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = CopilotTertiary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Evidence-Based Skills",
                                color = CopilotOnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = CopilotTertiary.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "Audit Ready",
                                    color = CopilotTertiary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = "${skills.size} Competency Proofs Verified",
                            color = CopilotOnSurfaceVariant,
                            fontSize = 11.sp
                        )
                    }
                }

                Button(
                    onClick = onAddClick,
                    modifier = Modifier
                        .height(34.dp)
                        .testTag("add_evidence_skill_btn"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CopilotPrimaryContainer,
                        contentColor = CopilotOnPrimaryContainer
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Add Evidence",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Explanation Callout
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = CopilotSurfaceContainerHigh,
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Shield,
                        contentDescription = null,
                        tint = CopilotTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Every skill is backed by cryptographically auditable artifacts: GitHub commits, production telemetry, or algorithmic test pass runs.",
                        color = CopilotOnSurfaceVariant,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Category Filter Pills
            val categoryScroll = rememberScrollState()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(categoryScroll),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = selectedCategory == category
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { selectedCategory = category },
                        color = if (isSelected) CopilotTertiaryContainer else CopilotSurfaceContainerHigh,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = category,
                            color = if (isSelected) CopilotTertiary else CopilotOnSurfaceVariant,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Skills List
            if (filteredSkills.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No skills in '$selectedCategory'. Click '+ Add Evidence' to register one.",
                        color = CopilotOutline,
                        fontSize = 12.sp
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    filteredSkills.forEach { skill ->
                        EvidenceSkillCard(
                            skill = skill,
                            onClick = { onSkillClick(skill) }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Individual Evidence Skill Card with verification badge,
 * progress bar, technical artifact snippet, and metrics pill.
 */
@Composable
fun EvidenceSkillCard(
    skill: EvidenceSkillEntity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val animatedProgress by animateFloatAsState(
        targetValue = skill.proficiencyPercentage / 100f,
        label = "skillProgress"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, CopilotOutlineVariant.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag("evidence_skill_card_${skill.id}"),
        color = CopilotSurfaceContainerHigh
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Top Row: Category icon, Name & Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(getCategoryColor(skill.category).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = getEvidenceIcon(skill.evidenceType),
                            contentDescription = null,
                            tint = getCategoryColor(skill.category),
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = skill.name,
                            color = CopilotOnSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = skill.category,
                            color = CopilotOutline,
                            fontSize = 10.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Verification Status Badge
                Surface(
                    color = CopilotTertiary.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = CopilotTertiary,
                            modifier = Modifier.size(10.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = formatStatus(skill.verificationStatus),
                            color = CopilotTertiary,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Proficiency Bar + Score
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(CircleShape),
                    color = getCategoryColor(skill.category),
                    trackColor = CopilotSurfaceContainerLowest
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${skill.proficiencyPercentage}% Verified",
                    color = CopilotOnSurfaceVariant,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Concrete Evidence Proof Title & Description
            Text(
                text = "Proof: ${skill.evidenceTitle}",
                color = CopilotSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = skill.evidenceDescription,
                color = CopilotOnSurfaceVariant,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Bottom Badges: Artifact Ref & Metrics
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Artifact reference pill
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(CopilotSurfaceContainerLowest)
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Link,
                        contentDescription = null,
                        tint = CopilotPrimary,
                        modifier = Modifier.size(11.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = skill.artifactUrlOrRef,
                        color = CopilotPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Metrics Pill
                Surface(
                    color = CopilotSecondaryContainer.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = skill.metricsPill,
                        color = CopilotOnSecondaryContainer,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

/**
 * Highlights verified skills on the Dashboard with competence signals
 */
@Composable
fun EvidenceSkillsDashboardCard(
    skills: List<EvidenceSkillEntity>,
    onExploreAllClick: () -> Unit,
    onSkillClick: (EvidenceSkillEntity) -> Unit,
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
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(CopilotTertiaryContainer.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = CopilotTertiary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Evidence-Based Skills",
                            color = CopilotOnSurface,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Audit-backed developer telemetry",
                            color = CopilotOnSurfaceVariant,
                            fontSize = 10.sp
                        )
                    }
                }

                TextButton(
                    onClick = onExploreAllClick,
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text(
                        text = "View All",
                        color = CopilotTertiary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = CopilotTertiary,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Show top 2-3 evidence skills
            val previewSkills = skills.take(3)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                previewSkills.forEach { skill ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onSkillClick(skill) },
                        color = CopilotSurfaceContainerHigh
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(getCategoryColor(skill.category))
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = skill.name,
                                        color = CopilotOnSurface,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = skill.evidenceTitle,
                                        color = CopilotOnSurfaceVariant,
                                        fontSize = 10.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }

                            Surface(
                                color = CopilotTertiary.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "${skill.proficiencyPercentage}%",
                                    color = CopilotTertiary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Dialog displaying full Proof Dossier for a selected skill
 */
@Composable
fun EvidenceSkillDossierDialog(
    skill: EvidenceSkillEntity,
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = CopilotSurfaceContainerLow),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                            .background(CopilotTertiaryContainer.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = null,
                                tint = CopilotTertiary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Evidence Dossier",
                                color = CopilotOnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Institutional & Peer Audit Proof",
                                color = CopilotOutline,
                                fontSize = 10.sp
                            )
                        }
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = CopilotOnSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Skill Title & Category Banner
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = CopilotSurfaceContainerHigh,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = skill.name,
                                color = CopilotOnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Surface(
                                color = CopilotTertiaryContainer,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "${skill.proficiencyPercentage}% Verified",
                                    color = CopilotTertiary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Text(
                            text = "Domain: ${skill.category}",
                            color = CopilotOnSurfaceVariant,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Evidence Details Section
                Text(
                    text = "VALIDATED EVIDENCE ARTIFACT",
                    color = CopilotOutline,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = CopilotSurfaceContainerLowest,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = getEvidenceIcon(skill.evidenceType),
                                contentDescription = null,
                                tint = CopilotSecondary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${skill.evidenceType} • ${skill.verifiedDate}",
                                color = CopilotSecondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = skill.evidenceTitle,
                            color = CopilotOnSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = skill.evidenceDescription,
                            color = CopilotOnSurfaceVariant,
                            fontSize = 11.sp,
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Metric & Artifact Link
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Artifact: ${skill.artifactUrlOrRef}",
                                color = CopilotPrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Surface(
                                color = CopilotSecondaryContainer,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = skill.metricsPill,
                                    color = CopilotOnSecondaryContainer,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Verification Authority Callout
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = CopilotTertiary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = CopilotTertiary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Audited by AI Career Copilot Telemetry Engine. Compatible with FAANG & AI Lab hiring schemas.",
                            color = CopilotTertiary,
                            fontSize = 10.sp,
                            lineHeight = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDelete,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = CopilotError),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CopilotError.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Remove", fontSize = 12.sp)
                    }

                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CopilotPrimaryContainer,
                            contentColor = CopilotOnPrimaryContainer
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Done", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

/**
 * Dialog to add a new Evidence-Based Skill into the system
 */
@Composable
fun AddEvidenceSkillDialog(
    onDismiss: () -> Unit,
    onSave: (
        name: String,
        category: String,
        proficiency: Int,
        evidenceType: String,
        evidenceTitle: String,
        evidenceDescription: String,
        artifactUrlOrRef: String,
        metricsPill: String
    ) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("AI & Deep Learning") }
    var proficiency by remember { mutableIntStateOf(90) }
    var evidenceType by remember { mutableStateOf("GitHub Repository") }
    var evidenceTitle by remember { mutableStateOf("") }
    var evidenceDescription by remember { mutableStateOf("") }
    var artifactUrl by remember { mutableStateOf("github.com/lokeshnagasaitungala/") }
    var metricsPill by remember { mutableStateOf("100% Pass • Audited") }

    val categories = listOf("AI & Deep Learning", "ML Systems & MLOps", "Algorithms & DSA", "Cloud & Backend")
    val evidenceTypes = listOf("GitHub Repository", "Production Metric", "Assessment Drill", "System Architecture")

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = CopilotSurfaceContainerLow),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Dialog Title
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Add Evidence-Based Skill",
                        color = CopilotOnSurface,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = CopilotOnSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Text(
                    text = "Link concrete technical proof to substantiate your skill proficiency.",
                    color = CopilotOnSurfaceVariant,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Skill Name Input
                Text(text = "SKILL NAME", color = CopilotOutline, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth().testTag("skill_name_input"),
                    placeholder = { Text("e.g. CUDA & GPU Kernels, PyTorch, Ray", color = CopilotOutlineVariant, fontSize = 12.sp) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = CopilotSurfaceContainerLowest,
                        unfocusedContainerColor = CopilotSurfaceContainerLowest,
                        focusedBorderColor = CopilotPrimary,
                        unfocusedBorderColor = CopilotSurfaceContainerHighest
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Domain Category Selection
                Text(text = "DOMAIN CATEGORY", color = CopilotOutline, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    categories.forEach { cat ->
                        val isSelected = category == cat
                        Surface(
                            modifier = Modifier.clickable { category = cat },
                            color = if (isSelected) CopilotTertiaryContainer else CopilotSurfaceContainerHigh,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = cat,
                                color = if (isSelected) CopilotTertiary else CopilotOnSurfaceVariant,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Proficiency Percentage Slider
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "PROFICIENCY SCORE", color = CopilotOutline, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(text = "$proficiency%", color = CopilotTertiary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Slider(
                    value = proficiency.toFloat(),
                    onValueChange = { proficiency = it.toInt() },
                    valueRange = 50f..100f,
                    colors = SliderDefaults.colors(
                        thumbColor = CopilotTertiary,
                        activeTrackColor = CopilotTertiary,
                        inactiveTrackColor = CopilotSurfaceContainerLowest
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Evidence Type Selector
                Text(text = "EVIDENCE TYPE", color = CopilotOutline, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    evidenceTypes.forEach { type ->
                        val isSelected = evidenceType == type
                        Surface(
                            modifier = Modifier.clickable { evidenceType = type },
                            color = if (isSelected) CopilotSecondaryContainer else CopilotSurfaceContainerHigh,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = type,
                                color = if (isSelected) CopilotOnSecondaryContainer else CopilotOnSurfaceVariant,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Proof Title
                Text(text = "PROOF ARTIFACT TITLE", color = CopilotOutline, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = evidenceTitle,
                    onValueChange = { evidenceTitle = it },
                    modifier = Modifier.fillMaxWidth().testTag("evidence_title_input"),
                    placeholder = { Text("e.g. Distributed Triton Inference Architecture", color = CopilotOutlineVariant, fontSize = 12.sp) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = CopilotSurfaceContainerLowest,
                        unfocusedContainerColor = CopilotSurfaceContainerLowest,
                        focusedBorderColor = CopilotPrimary,
                        unfocusedBorderColor = CopilotSurfaceContainerHighest
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Proof Technical Description
                Text(text = "TECHNICAL PROOF DETAILS", color = CopilotOutline, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = evidenceDescription,
                    onValueChange = { evidenceDescription = it },
                    modifier = Modifier.fillMaxWidth().height(80.dp).testTag("evidence_desc_input"),
                    placeholder = { Text("Describe the architecture, benchmarks, or test pass rate...", color = CopilotOutlineVariant, fontSize = 12.sp) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = CopilotSurfaceContainerLowest,
                        unfocusedContainerColor = CopilotSurfaceContainerLowest,
                        focusedBorderColor = CopilotPrimary,
                        unfocusedBorderColor = CopilotSurfaceContainerHighest
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Artifact Link & Metric Tag
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1.2f)) {
                        Text(text = "ARTIFACT URL / REF", color = CopilotOutline, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = artifactUrl,
                            onValueChange = { artifactUrl = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = CopilotSurfaceContainerLowest,
                                unfocusedContainerColor = CopilotSurfaceContainerLowest,
                                focusedBorderColor = CopilotPrimary,
                                unfocusedBorderColor = CopilotSurfaceContainerHighest
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(0.8f)) {
                        Text(text = "METRICS TAG", color = CopilotOutline, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = metricsPill,
                            onValueChange = { metricsPill = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = CopilotSurfaceContainerLowest,
                                unfocusedContainerColor = CopilotSurfaceContainerLowest,
                                focusedBorderColor = CopilotPrimary,
                                unfocusedBorderColor = CopilotSurfaceContainerHighest
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Cancel", fontSize = 12.sp)
                    }

                    Button(
                        onClick = {
                            if (name.isNotBlank()) {
                                onSave(
                                    name,
                                    category,
                                    proficiency,
                                    evidenceType,
                                    if (evidenceTitle.isBlank()) "$name Verified Project" else evidenceTitle,
                                    if (evidenceDescription.isBlank()) "Validated with production tests and codebase audit." else evidenceDescription,
                                    artifactUrl,
                                    metricsPill
                                )
                            }
                        },
                        enabled = name.isNotBlank(),
                        modifier = Modifier.weight(1f).testTag("save_evidence_skill_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CopilotPrimaryContainer,
                            contentColor = CopilotOnPrimaryContainer
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Save Proof", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

private fun getCategoryColor(category: String): Color {
    return when (category.lowercase()) {
        "ai & deep learning" -> CopilotTertiary
        "ml systems & mlops" -> CopilotSecondary
        "algorithms & dsa" -> CopilotPrimary
        "cloud & backend" -> CopilotCyan
        else -> CopilotEmerald
    }
}

private fun getEvidenceIcon(evidenceType: String): ImageVector {
    return when (evidenceType.lowercase()) {
        "github repository" -> Icons.Outlined.Code
        "production metric" -> Icons.Outlined.Speed
        "assessment drill" -> Icons.Outlined.Assessment
        "system architecture" -> Icons.Outlined.Storage
        else -> Icons.Outlined.Verified
    }
}

private fun formatStatus(status: String): String {
    return when (status) {
        "CODE_AUDITED" -> "Code Audited"
        "BENCHMARK_VALIDATED" -> "Benchmark Validated"
        "ASSESSMENT_PASSED" -> "Assessment Passed"
        "VERIFIED_PROOF" -> "Verified Proof"
        else -> "Audited"
    }
}
