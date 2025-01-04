package com.ensas.medivault.data.repository

import com.ensas.medivault.data.InitialData
import com.ensas.medivault.data.dao.FakeDao
import com.ensas.medivault.data.dao.MedicationDao
import com.ensas.medivault.data.model.Medication
import javax.inject.Inject

// Repository to manage Medication data interactions with the DAO
open class MedicationRepository @Inject constructor(private val medicationDao: MedicationDao) {
    // Retrieves all medications from the database
    open suspend fun getMedications(): List<Medication> {
        return medicationDao.getAllMedications()
    }

    // Retrieves a specific medication by ID
    suspend fun getMedicationById(id: Int): Medication? {
        return medicationDao.getMedicationById(id)
    }

    // Inserts a list of medications into the database
    suspend fun insertMedications(medications: List<Medication>) {
        medicationDao.insertMedications(medications)
    }

    // Inserts a single medication into the database
    suspend fun insertMedication(medication: Medication) {
        medicationDao.insertMedication(medication)
    }
}

// Fake repository for providing data during previews or testing
class FakeRepository : MedicationRepository(FakeDao()) {
    override suspend fun getMedications(): List<Medication> {
        return InitialData.medications
    }
}