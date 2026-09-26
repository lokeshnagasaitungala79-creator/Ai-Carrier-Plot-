package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.ui.AuthUiState
import com.example.ui.CopilotViewModel
import com.example.ui.theme.*

@Composable
fun AuthScreen(
    viewModel: CopilotViewModel,
    modifier: Modifier = Modifier
) {
    val authState by viewModel.authUiState.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CopilotSurface)
    ) {
        // Atmospheric gradient accents in background
        Box(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.TopEnd)
                .offset(x = 60.dp, y = (-40).dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(CopilotPrimaryContainer.copy(alpha = 0.15f), Color.Transparent)
                    )
                )
        )
        Box(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-60).dp, y = 60.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(CopilotTertiary.copy(alpha = 0.12f), Color.Transparent)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main Card Container
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, CopilotOutlineVariant.copy(alpha = 0.35f), RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = CopilotSurfaceContainerLow),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo with glowing pulse
                    Box(
                        modifier = Modifier
                            .size(68.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(CopilotSurfaceContainerHigh)
                            .border(1.dp, CopilotPrimary.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida/AEtjO1XZkIvsuaNr8GBbnkuy7q_aVq455Nq28NLkiDZrQFCjjPHLlFHC4h0YNw-4a8jD6WjVEEu8_PKWH6b9yzMkldEHMmZ0mlefpvhCmLWMNTpRVNPQUFJoUDwB3tniizoPer7En9EAeWtxajsMnI38IPUfdbbVmzWYtYEPQulQPAB8SxVH_clIBD7JQ9RVvJIf4LHdtigNUCOhevI3vc-9DLW7aRBi2nmXcR7oAObf0AoDkRjHdH7BHawPfb0v",
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            placeholder = painterResource(id = R.drawable.ic_copilot_delta),
                            error = painterResource(id = R.drawable.ic_copilot_delta)
                        )
                        // Pulsing online dot
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .align(Alignment.BottomEnd)
                                .offset(x = 2.dp, y = 2.dp)
                                .clip(CircleShape)
                                .background(CopilotSurfaceContainerHigh)
                                .padding(2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .background(CopilotTertiary)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Institutional SSO Badge
                    Surface(
                        color = CopilotSurfaceContainerHighest.copy(alpha = 0.7f),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.VerifiedUser,
                                contentDescription = null,
                                tint = CopilotTertiary,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "INSTITUTIONAL SSO ACTIVE",
                                color = CopilotTertiary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    // Title & Description
                    Text(
                        text = "AI Career Copilot",
                        color = CopilotOnSurface,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-0.5).sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Your AI-powered journey from ambitious student to verified job-ready professional.",
                        color = CopilotOnSurfaceVariant,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Mode Selector Tabs (Sign In / Create Account)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CopilotSurfaceContainerLowest, RoundedCornerShape(10.dp))
                            .padding(4.dp)
                    ) {
                        TabPill(
                            title = "Sign In",
                            icon = Icons.Default.Login,
                            isSelected = !authState.isSignUpTab,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.setAuthTab(false) }
                        )
                        TabPill(
                            title = "Create Account",
                            icon = Icons.Default.PersonAdd,
                            isSelected = authState.isSignUpTab,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.setAuthTab(true) }
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Demo Quick Access Pill (Instant Evaluator Gateway)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, CopilotSecondaryContainer.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                            .clickable { viewModel.fillDemoSandboxAndLogin() }
                            .testTag("demo_sandbox_button"),
                        color = CopilotSurfaceContainerHigh
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(CopilotSecondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Bolt,
                                    contentDescription = null,
                                    tint = CopilotOnSecondaryContainer,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "1-Click Evaluator Sandbox",
                                        color = CopilotOnSurface,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        color = CopilotTertiary.copy(alpha = 0.15f),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = "Live Spec",
                                            color = CopilotTertiary,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "Explore as Lokesh Naga Sai (ML Engineer Track)",
                                    color = CopilotOnSurfaceVariant,
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = CopilotPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Form Fields
                    // Academic / Tech Identity
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ACADEMIC / TECH IDENTITY",
                                color = CopilotOnSurfaceVariant,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(CopilotTertiary)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = ".edu eligible",
                                    color = CopilotTertiary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = authState.email,
                            onValueChange = { viewModel.updateEmail(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("email_input"),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.School,
                                    contentDescription = null,
                                    tint = CopilotOnSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            placeholder = {
                                Text("student@university.edu", color = CopilotOutlineVariant, fontSize = 13.sp)
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = CopilotSurfaceContainerLowest,
                                unfocusedContainerColor = CopilotSurfaceContainerLowest,
                                focusedBorderColor = CopilotPrimary,
                                unfocusedBorderColor = CopilotSurfaceContainerHighest,
                                focusedTextColor = CopilotOnSurface,
                                unfocusedTextColor = CopilotOnSurface
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Access Key
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ACCESS KEY",
                                color = CopilotOnSurfaceVariant,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Forgot password?",
                                color = CopilotPrimary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.clickable { }
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        OutlinedTextField(
                            value = authState.accessKey,
                            onValueChange = { viewModel.updateAccessKey(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("password_input"),
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Outlined.Lock,
                                    contentDescription = null,
                                    tint = CopilotOnSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { viewModel.togglePasswordVisibility() },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = if (authState.isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = "Toggle visibility",
                                        tint = CopilotOnSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            },
                            visualTransformation = if (authState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            placeholder = {
                                Text("Enter access key", color = CopilotOutlineVariant, fontSize = 13.sp)
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { viewModel.launchCopilotSession() }),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = CopilotSurfaceContainerLowest,
                                unfocusedContainerColor = CopilotSurfaceContainerLowest,
                                focusedBorderColor = CopilotPrimary,
                                unfocusedBorderColor = CopilotSurfaceContainerHighest,
                                focusedTextColor = CopilotOnSurface,
                                unfocusedTextColor = CopilotOnSurface
                            ),
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    if (authState.errorMessage != null) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = CopilotErrorContainer.copy(alpha = 0.35f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CopilotError.copy(alpha = 0.5f))
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = CopilotError,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = authState.errorMessage!!,
                                    color = CopilotError,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }

                    // Launch Copilot Session Button
                    Button(
                        onClick = { viewModel.launchCopilotSession() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("submit_auth_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CopilotPrimaryContainer,
                            contentColor = CopilotOnPrimaryContainer
                        ),
                        shape = RoundedCornerShape(10.dp),
                        enabled = !authState.isLoading
                    ) {
                        if (authState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = CopilotOnPrimaryContainer,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = authState.statusMessage ?: "Authenticating...",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Terminal,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (authState.isSignUpTab) "Initialize Developer Profile" else "Launch Copilot Session",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // Social Divider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = CopilotSurfaceContainerHighest
                        )
                        Text(
                            text = "INSTITUTIONAL SSO & FAST ACCESS",
                            color = CopilotOutline,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = CopilotSurfaceContainerHighest
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Social Fast Access Buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FastAuthButton(
                            title = "Continue with Google (College SSO)",
                            iconType = "google",
                            onClick = { viewModel.launchCopilotSession() }
                        )
                        FastAuthButton(
                            title = "Continue with GitHub Developer Auth",
                            iconType = "github",
                            onClick = { viewModel.launchCopilotSession() }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Intelligent Suite Modules Section
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "INTELLIGENT SUITE MODULES",
                        color = CopilotOutline,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "3 Engines Active",
                        color = CopilotSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                ModuleCard(
                    icon = Icons.Outlined.DocumentScanner,
                    iconBg = CopilotPrimaryContainer.copy(alpha = 0.2f),
                    iconTint = CopilotPrimary,
                    title = "AI Resume & ATS Gap Analysis",
                    tag = "98.4% Match",
                    tagColor = CopilotTertiary,
                    description = "Parse tech specs against FAANG job descriptions in real-time."
                )

                Spacer(modifier = Modifier.height(8.dp))

                ModuleCard(
                    icon = Icons.Outlined.AltRoute,
                    iconBg = CopilotSecondaryContainer.copy(alpha = 0.35f),
                    iconTint = CopilotSecondary,
                    title = "Dynamic Adaptive Roadmap",
                    tag = "Kinetic Path",
                    tagColor = CopilotSecondary,
                    description = "Custom skill graph recalculating based on weekly project commits."
                )

                Spacer(modifier = Modifier.height(8.dp))

                ModuleCard(
                    icon = Icons.Outlined.Code,
                    iconBg = CopilotTertiaryContainer.copy(alpha = 0.25f),
                    iconTint = CopilotTertiary,
                    title = "AI Mock Voice & DSA Copilot",
                    tag = "Live Audio",
                    tagColor = CopilotTertiary,
                    description = "Simulated LeetCode system design & algorithmic stress tests."
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Trust / Peer Stats Micro Banner
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = CopilotSurfaceContainerLowest
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.offset(x = 0.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BadgeCircle(text = "MIT", bg = CopilotPrimaryContainer, fg = CopilotOnPrimaryContainer)
                        BadgeCircle(text = "STAN", bg = CopilotSecondaryContainer, fg = CopilotOnSecondaryContainer, modifier = Modifier.offset(x = (-6).dp))
                        BadgeCircle(text = "CMU", bg = CopilotTertiaryContainer, fg = CopilotTertiary, modifier = Modifier.offset(x = (-12).dp))
                    }
                    Text(
                        text = "Adopted by 14,200+ CS & STEM grads",
                        color = CopilotOnSurfaceVariant,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Compliance & Security Footnote
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "By continuing, you acknowledge our Institutional Terms and FERPA Privacy Standards.",
                    color = CopilotOutlineVariant,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = CopilotOutlineVariant,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "256-bit End-to-End Encrypted Telemetry",
                        color = CopilotOutlineVariant,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun TabPill(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },
        color = if (isSelected) CopilotSurfaceContainerHigh else Color.Transparent,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) CopilotPrimary else CopilotOnSurfaceVariant,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = title,
                color = if (isSelected) CopilotPrimary else CopilotOnSurfaceVariant,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}

@Composable
private fun FastAuthButton(
    title: String,
    iconType: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        color = CopilotSurfaceContainerHigh,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(vertical = 11.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (iconType == "google") {
                // Colored G icon indicator
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4285F4)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "G",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Default.Code,
                    contentDescription = null,
                    tint = CopilotOnSurface,
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                color = CopilotOnSurface,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun ModuleCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    tag: String,
    tagColor: Color,
    description: String
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = CopilotSurfaceContainerHigh.copy(alpha = 0.7f),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        color = CopilotOnSurface,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = tag,
                        color = tagColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Text(
                    text = description,
                    color = CopilotOnSurfaceVariant,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun BadgeCircle(
    text: String,
    bg: Color,
    fg: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(bg)
            .border(1.5.dp, CopilotSurfaceContainerLowest, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = fg,
            fontSize = 7.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
