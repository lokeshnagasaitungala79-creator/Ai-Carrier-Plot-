package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("AI Career Copilot", appName)
  }

  @Test
  fun `verify authorized credentials requirement`() {
    val context = ApplicationProvider.getApplicationContext<android.app.Application>()
    val viewModel = com.example.ui.CopilotViewModel(context)

    // Check email verification
    org.junit.Assert.assertTrue(viewModel.isAuthorizedEmail("lokeshnagasaitungala79@gmail.com"))
    org.junit.Assert.assertFalse(viewModel.isAuthorizedEmail("stranger@gmail.com"))

    // Check access key verification
    org.junit.Assert.assertEquals("", viewModel.authUiState.value.accessKey)
    org.junit.Assert.assertTrue(viewModel.isAuthorizedAccessKey("Lokesh Naga Sai 143"))
    org.junit.Assert.assertFalse(viewModel.isAuthorizedAccessKey("WrongKey123"))
  }

  @Test
  fun `verify evidence-based skills flow`() {
    val context = ApplicationProvider.getApplicationContext<android.app.Application>()
    val viewModel = com.example.ui.CopilotViewModel(context)

    // Verify dialog toggles
    org.junit.Assert.assertFalse(viewModel.showAddEvidenceSkillDialog.value)
    viewModel.openAddEvidenceSkillDialog()
    org.junit.Assert.assertTrue(viewModel.showAddEvidenceSkillDialog.value)
    viewModel.closeAddEvidenceSkillDialog()
    org.junit.Assert.assertFalse(viewModel.showAddEvidenceSkillDialog.value)

    // Add a custom evidence skill
    viewModel.addEvidenceSkill(
        name = "Distributed PyTorch",
        category = "AI & Deep Learning",
        proficiency = 95,
        evidenceType = "GitHub Repository",
        evidenceTitle = "Megatron-LM Style Parallelism",
        evidenceDescription = "Implemented tensor and pipeline parallelism across distributed nodes.",
        artifactUrlOrRef = "github.com/lokeshnagasaitungala/parallelism",
        metricsPill = "4.5K LOC • Audited"
    )

    // Dossier inspection
    val sampleSkill = com.example.data.model.EvidenceSkillEntity(
        id = 99,
        name = "Test Skill",
        category = "AI & Deep Learning",
        proficiencyPercentage = 90,
        evidenceType = "GitHub Repository",
        evidenceTitle = "Test Proof",
        evidenceDescription = "Testing"
    )
    viewModel.openEvidenceSkillDossier(sampleSkill)
    org.junit.Assert.assertEquals(sampleSkill, viewModel.selectedEvidenceSkillForDossier.value)
    viewModel.closeEvidenceSkillDossier()
    org.junit.Assert.assertNull(viewModel.selectedEvidenceSkillForDossier.value)
  }
}
