package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.UserProfileEntity
import com.example.ui.theme.*

@Composable
fun EditProfileDialog(
    profile: UserProfileEntity,
    onDismiss: () -> Unit,
    onSave: (name: String, targetRole: String, objective: String, studyHours: Int, gpa: String, institution: String) -> Unit
) {
    var name by remember { mutableStateOf(profile.name) }
    var targetRole by remember { mutableStateOf(profile.targetRole) }
    var objective by remember { mutableStateOf(profile.primaryObjective) }
    var studyHours by remember { mutableIntStateOf(profile.dailyStudyGoalHours) }
    var gpa by remember { mutableStateOf(profile.gpa) }
    var institution by remember { mutableStateOf(profile.institution) }

    val scrollState = rememberScrollState()

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
                    .verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Edit Profile & Goals",
                        color = CopilotOnSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = CopilotOnSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Name
                Text(text = "Full Name", color = CopilotOnSurfaceVariant, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = CopilotSurfaceContainerLowest,
                        unfocusedContainerColor = CopilotSurfaceContainerLowest,
                        focusedTextColor = CopilotOnSurface,
                        unfocusedTextColor = CopilotOnSurface
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Target Role
                Text(text = "Target Role Track", color = CopilotOnSurfaceVariant, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = targetRole,
                    onValueChange = { targetRole = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = CopilotSurfaceContainerLowest,
                        unfocusedContainerColor = CopilotSurfaceContainerLowest,
                        focusedTextColor = CopilotOnSurface,
                        unfocusedTextColor = CopilotOnSurface
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Primary Objective
                Text(text = "Primary Objective", color = CopilotOnSurfaceVariant, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = objective,
                    onValueChange = { objective = it },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = CopilotSurfaceContainerLowest,
                        unfocusedContainerColor = CopilotSurfaceContainerLowest,
                        focusedTextColor = CopilotOnSurface,
                        unfocusedTextColor = CopilotOnSurface
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Institution & GPA
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "GPA", color = CopilotOnSurfaceVariant, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = gpa,
                            onValueChange = { gpa = it },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = CopilotSurfaceContainerLowest,
                                unfocusedContainerColor = CopilotSurfaceContainerLowest,
                                focusedTextColor = CopilotOnSurface,
                                unfocusedTextColor = CopilotOnSurface
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Daily Hours", color = CopilotOnSurfaceVariant, fontSize = 11.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = "$studyHours",
                            onValueChange = { str -> str.toIntOrNull()?.let { studyHours = it } },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = CopilotSurfaceContainerLowest,
                                unfocusedContainerColor = CopilotSurfaceContainerLowest,
                                focusedTextColor = CopilotOnSurface,
                                unfocusedTextColor = CopilotOnSurface
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        onSave(name, targetRole, objective, studyHours, gpa, institution)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CopilotPrimaryContainer,
                        contentColor = CopilotOnPrimaryContainer
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Save Changes", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}
