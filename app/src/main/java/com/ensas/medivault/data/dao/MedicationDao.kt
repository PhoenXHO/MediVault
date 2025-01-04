package com.ensas.medivault.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ensas.medivault.data.InitialData
import com.ensas.medivault.data.model.Medication

@Dao
interface MedicationDao {
    // Retrieves all medications from the medications table
    @Query("SELECT * FROM medications")
    suspend fun getAllMedications(): List<Medication>

    // Retrieves a medication by its ID
    @Query("SELECT * FROM medications WHERE id = :medicationId LIMIT 1")
    suspend fun getMedicationById(medicationId: Int): Medication?

    // Inserts a list of medications, replacing on conflict
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedications(medications: List<Medication>)

    // Inserts a single medication, replacing on conflict
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: Medication)
}

// Fake DAO for providing data during previews or testing
public class FakeDao : MedicationDao {
    override suspend fun getAllMedications(): List<Medication> {
        return InitialData.medications
    }

    override suspend fun getMedicationById(medicationId: Int): Medication? {
        return InitialData.medications.find { it.id == medicationId }
    }

    override suspend fun insertMedications(medications: List<Medication>) {
        // Not required for preview
    }

    override suspend fun insertMedication(medication: Medication) {
        // Not required for preview
    }
}