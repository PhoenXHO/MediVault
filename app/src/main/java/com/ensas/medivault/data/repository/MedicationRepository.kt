package com.ensas.medivault.data.repository

import com.ensas.medivault.data.model.Medication

// To store the medications
class MedicationRepository {
    private val medications = listOf(
        Medication("1", "Medication 1", "Description 1", 10.0),
        Medication("2", "Medication 2", "Description 2", 20.0),
        Medication("3", "Medication 3", "Description 3", 30.0),
        Medication("4", "Medication 4", "Description 4", 40.0),
        Medication("5", "Medication 5", "Description 5", 50.0),
        Medication("6", "Medication 6", "Description 6", 60.0),
        Medication("7", "Medication 7", "Description 7", 70.0),
        Medication("8", "Medication 8", "Description 8", 80.0),
        Medication("9", "Medication 9", "Description 9", 90.0),
        Medication("10", "Medication 10", "Description 10", 100.0),
        Medication("11", "Medication 11", "Description 11", 110.0),
        Medication("12", "Medication 12", "Description 12", 120.0),
        Medication("13", "Medication 13", "Description 13", 130.0),
        Medication("14", "Medication 14", "Description 14", 140.0),
        Medication("15", "Medication 15", "Description 15", 150.0),
        Medication("16", "Medication 16", "Description 16", 160.0),
        Medication("17", "Medication 17", "Description 17", 170.0),
        Medication("18", "Medication 18", "Description 18", 180.0),
        Medication("19", "Medication 19", "Description 19", 190.0),
        Medication("20", "Medication 20", "Description 20", 200.0),
        Medication("21", "Medication 21", "Description 21", 210.0),
        Medication("22", "Medication 22", "Description 22", 220.0),
        Medication("23", "Medication 23", "Description 23", 230.0),
        Medication("24", "Medication 24", "Description 24", 240.0),
        Medication("25", "Medication 25", "Description 25", 250.0),
        Medication("26", "Medication 26", "Description 26", 260.0),
    )

    fun getMedicationById(id: String): Medication? {
        return medications.find { it.id == id }
    }

    fun getMedications(): List<Medication> {
        return medications
    }
}