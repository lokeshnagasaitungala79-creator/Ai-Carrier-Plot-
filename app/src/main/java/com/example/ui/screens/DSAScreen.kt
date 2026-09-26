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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.CopilotViewModel
import com.example.ui.theme.*

data class DSAProblemItem(
    val id: Int,
    val title: String,
    val difficulty: String,
    val acceptance: String,
    val topic: String,
    val isSolved: Boolean
)

@Composable
fun DSAScreen(
    viewModel: CopilotViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    var selectedProblemId by remember { mutableIntStateOf(2) } // default "Search in Rotated Sorted Array"
    var testResultText by remember { mutableStateOf<String?>(null) }
    var isRunningTest by remember { mutableStateOf(false) }

    val problems = remember {
        mutableStateListOf(
            DSAProblemItem(1, "704. Binary Search", "Easy", "78.4%", "Binary Search", true),
            DSAProblemItem(2, "33. Search in Rotated Sorted Array", "Medium", "55.2%", "Binary Search", false),
            DSAProblemItem(3, "153. Find Min in Rotated Sorted Array", "Medium", "62.1%", "Binary Search", false),
            DSAProblemItem(4, "4. Median of Two Sorted Arrays", "Hard", "39.5%", "Binary Search", false),
            DSAProblemItem(5, "200. Number of Islands", "Medium", "58.9%", "Graphs", true),
            DSAProblemItem(6, "300. Longest Increasing Subsequence", "Medium", "54.0%", "DP", false)
        )
    }

    val selectedProblem = problems.find { it.id == selectedProblemId } ?: problems[1]

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
                    text = "DSA & Algorithmic Rigor",
                    color = CopilotOnSurface,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "AI Recommendation Lab • Binary Search Focus",
                    color = CopilotOnSurfaceVariant,
                    fontSize = 12.sp
                )
            }

            Surface(
                color = CopilotTertiaryContainer.copy(alpha = 0.3f),
                shape = RoundedCornerShape(50)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = CopilotTertiary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "+120 XP",
                        color = CopilotTertiary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Focus Lab Problem Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, CopilotSecondaryContainer.copy(alpha = 0.5f), RoundedCornerShape(14.dp)),
            colors = CardDefaults.cardColors(containerColor = CopilotSurfaceContainerLow),
            shape = RoundedCornerShape(14.dp)
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
                    Surface(
                        color = when (selectedProblem.difficulty) {
                            "Easy" -> CopilotTertiaryContainer.copy(alpha = 0.3f)
                            "Medium" -> CopilotAmber.copy(alpha = 0.2f)
                            else -> CopilotErrorContainer.copy(alpha = 0.3f)
                        },
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = selectedProblem.difficulty,
                            color = when (selectedProblem.difficulty) {
                                "Easy" -> CopilotTertiary
                                "Medium" -> CopilotAmber
                                else -> CopilotError
                            },
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = "Acceptance: ${selectedProblem.acceptance}",
                        color = CopilotOnSurfaceVariant,
                        fontSize = 11.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = selectedProblem.title,
                    color = CopilotOnSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Given an integer array nums sorted in ascending order (with distinct values) and a target, find the target's index in O(log n) runtime complexity.",
                    color = CopilotOnSurfaceVariant,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Interactive Pointer Step-Through Visualizer
                Text(
                    text = "STEP-BY-STEP CONVERGENCE TRACE",
                    color = CopilotOutline,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = CopilotSurfaceContainerLowest,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            val items = listOf("4", "5", "6", "7", "0", "1", "2")
                            items.forEachIndexed { idx, value ->
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Surface(
                                        modifier = Modifier.size(32.dp),
                                        color = if (idx == 3) CopilotSecondaryContainer else CopilotSurfaceContainerHigh,
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(
                                                text = value,
                                                color = if (idx == 3) CopilotOnSecondaryContainer else CopilotOnSurface,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = when (idx) {
                                            0 -> "L"
                                            3 -> "MID"
                                            6 -> "R"
                                            else -> ""
                                        },
                                        color = when (idx) {
                                            0 -> CopilotTertiary
                                            3 -> CopilotSecondary
                                            6 -> CopilotCyan
                                            else -> Color.Transparent
                                        },
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Solution Code Snippet
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = CopilotSurfaceContainerLowest,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = """
def search(nums: List[int], target: int) -> int:
    left, right = 0, len(nums) - 1
    while left <= right:
        mid = (left + right) // 2
        if nums[mid] == target:
            return mid
        # Identify sorted half
        if nums[left] <= nums[mid]:
            if nums[left] <= target < nums[mid]:
                right = mid - 1
            else:
                left = mid + 1
        else:
            if nums[mid] < target <= nums[right]:
                left = mid + 1
            else:
                right = mid - 1
    return -1
                            """.trimIndent(),
                            color = CopilotOnSurface,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            lineHeight = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (testResultText != null) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = CopilotTertiaryContainer.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = CopilotTertiary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = testResultText!!,
                                color = CopilotTertiary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Button(
                    onClick = {
                        isRunningTest = true
                        testResultText = "Verifying test cases..."
                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                            isRunningTest = false
                            testResultText = "All 42 Test Cases Passed! +40 XP & DSA Score calibrated."
                            val index = problems.indexOfFirst { it.id == selectedProblemId }
                            if (index != -1) {
                                problems[index] = problems[index].copy(isSolved = true)
                            }
                        }, 600)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CopilotPrimaryContainer,
                        contentColor = CopilotOnPrimaryContainer
                    ),
                    shape = RoundedCornerShape(8.dp),
                    enabled = !isRunningTest
                ) {
                    if (isRunningTest) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            color = CopilotOnPrimaryContainer,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Execute Tests & Submit Solution",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Curated Track Problems List
        Text(
            text = "TARGET ROLE INTERVIEW QUESTIONS",
            color = CopilotOutline,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        problems.forEach { item ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        selectedProblemId = item.id
                        testResultText = null
                    },
                color = if (item.id == selectedProblemId) CopilotSurfaceContainerHigh else CopilotSurfaceContainerLow,
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (item.id == selectedProblemId) CopilotPrimary.copy(alpha = 0.5f) else CopilotOutlineVariant.copy(alpha = 0.2f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (item.isSolved) Icons.Default.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = if (item.isSolved) CopilotTertiary else CopilotOutline,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.title,
                            color = CopilotOnSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "${item.topic} • Acc: ${item.acceptance}",
                            color = CopilotOnSurfaceVariant,
                            fontSize = 10.sp
                        )
                    }

                    Surface(
                        color = when (item.difficulty) {
                            "Easy" -> CopilotTertiaryContainer.copy(alpha = 0.3f)
                            "Medium" -> CopilotAmber.copy(alpha = 0.2f)
                            else -> CopilotErrorContainer.copy(alpha = 0.3f)
                        },
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = item.difficulty,
                            color = when (item.difficulty) {
                                "Easy" -> CopilotTertiary
                                "Medium" -> CopilotAmber
                                else -> CopilotError
                            },
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
