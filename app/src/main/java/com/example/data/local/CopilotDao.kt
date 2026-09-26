package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.EvidenceSkillEntity
import com.example.data.model.SkillItemEntity
import com.example.data.model.SkillVectorEntity
import com.example.data.model.StudyTaskEntity
import com.example.data.model.UserProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CopilotDao {
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: UserProfileEntity)

    @Query("SELECT * FROM study_tasks ORDER BY id ASC")
    fun getAllStudyTasks(): Flow<List<StudyTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudyTasks(tasks: List<StudyTaskEntity>)

    @Update
    suspend fun updateStudyTask(task: StudyTaskEntity)

    @Query("SELECT * FROM skill_vectors ORDER BY id ASC")
    fun getSkillVectors(): Flow<List<SkillVectorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkillVectors(vectors: List<SkillVectorEntity>)

    @Query("SELECT * FROM skills_matrix ORDER BY id ASC")
    fun getSkillsMatrix(): Flow<List<SkillItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSkillsMatrix(items: List<SkillItemEntity>)

    @Query("SELECT * FROM evidence_skills ORDER BY proficiencyPercentage DESC")
    fun getAllEvidenceSkills(): Flow<List<EvidenceSkillEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvidenceSkill(skill: EvidenceSkillEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvidenceSkills(skills: List<EvidenceSkillEntity>)

    @Delete
    suspend fun deleteEvidenceSkill(skill: EvidenceSkillEntity)
}
