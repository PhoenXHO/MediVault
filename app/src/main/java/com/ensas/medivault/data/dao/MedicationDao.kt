package com.ensas.medivault.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ensas.medivault.data.InitialData
import com.ensas.medivault.data.model.Medication

@Dao
interface MedicationDao {
    @Query("SELECT * FROM medications")
    suspend fun getAllMedications(): List<Medication>

    @Query("SELECT * FROM medications WHERE id = :medicationId LIMIT 1")
    suspend fun getMedicationById(medicationId: String): Medication?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedications(medications: List<Medication>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: Medication)
}

// Fake DAO to provide data for preview
public class FakeDao : MedicationDao {
    override suspend fun getAllMedications(): List<Medication> {
        return InitialData.medications
    }

    override suspend fun getMedicationById(medicationId: String): Medication? {
        return InitialData.medications.find { it.id == medicationId }
    }

    override suspend fun insertMedications(medications: List<Medication>) {
        // Not required for preview
    }

    override suspend fun insertMedication(medication: Medication) {
        // Not required for preview
    }
}