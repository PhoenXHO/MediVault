package com.ensas.medivault.data.repository

import com.ensas.medivault.data.dao.MedicationDao
import com.ensas.medivault.data.model.Medication
import javax.inject.Inject

class MedicationRepository @Inject constructor(private val medicationDao: MedicationDao) {
    suspend fun getMedications(): List<Medication> {
        return medicationDao.getAllMedications()
    }

    suspend fun getMedicationById(id: String): Medication? {
        return medicationDao.getMedicationById(id)
    }

    suspend fun insertMedications(medications: List<Medication>) {
        medicationDao.insertMedications(medications)
    }

    suspend fun insertMedication(medication: Medication) {
        medicationDao.insertMedication(medication)
    }
}