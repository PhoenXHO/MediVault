package com.ensas.medivault.data.repository

import com.ensas.medivault.data.InitialData
import com.ensas.medivault.data.dao.FakeDao
import com.ensas.medivault.data.dao.MedicationDao
import com.ensas.medivault.data.model.Medication
import javax.inject.Inject

open class MedicationRepository @Inject constructor(private val medicationDao: MedicationDao) {
    open suspend fun getMedications(): List<Medication> {
        return medicationDao.getAllMedications()
    }

    suspend fun getMedicationById(id: Int): Medication? {
        return medicationDao.getMedicationById(id)
    }

    suspend fun insertMedications(medications: List<Medication>) {
        medicationDao.insertMedications(medications)
    }

    suspend fun insertMedication(medication: Medication) {
        medicationDao.insertMedication(medication)
    }
}

// Fake repository to provide data for preview
class FakeRepository : MedicationRepository(FakeDao()) {
    override suspend fun getMedications(): List<Medication> {
        return InitialData.medications
    }
}