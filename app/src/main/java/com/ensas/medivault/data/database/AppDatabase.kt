package com.ensas.medivault.data.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ensas.medivault.data.InitialData
import com.ensas.medivault.data.dao.MedicationDao
import com.ensas.medivault.data.model.Medication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Medication::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Retrieves the singleton instance of AppDatabase
        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "medivault_database"
                )
                    .addCallback(AppDatabaseCallback(scope)) // Add callback for pre-population
                    .build()
                INSTANCE = instance
                instance
            }
        }

        // Callback to populate the database when it's first created
        private class AppDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d("AppDatabase", "onCreate")
                INSTANCE?.let { database ->
                    scope.launch {
                        Log.d("AppDatabase", "Populating the database with initial data...")
                        // Insert initial medications into the database
                        val medicationDao = database.medicationDao()
                        InitialData.medications.forEach { medication ->
                            medicationDao.insertMedication(medication)
                        }
                    }
                }
            }
        }
    }
}