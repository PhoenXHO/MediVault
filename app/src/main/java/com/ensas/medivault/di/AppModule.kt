package com.ensas.medivault.di

import android.content.Context
import com.ensas.medivault.data.UserPreferences
import com.ensas.medivault.data.dao.MedicationDao
import com.ensas.medivault.data.database.AppDatabase
import com.ensas.medivault.data.repository.FavoritesRepository
import com.ensas.medivault.data.repository.FavoritesRepositoryInterface
import com.ensas.medivault.data.repository.MedicationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

// Module to provide application-level dependencies using Dagger Hilt
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindFavoritesRepository(favoritesRepository: FavoritesRepository)
        : FavoritesRepositoryInterface

    companion object {
        // Provides a singleton instance of UserPreferences
        @Provides
        @Singleton
        fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
            return UserPreferences(context)
        }

        // Provides a singleton instance of FirebaseAuth
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

        // Provides a singleton instance of FirebaseFirestore
        @Provides
        @Singleton
        fun provideFirebaseFirestore(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }

        // Provides a singleton CoroutineScope
        @Provides
        @Singleton
        fun provideCoroutineScope(): CoroutineScope {
            return CoroutineScope(SupervisorJob())
        }

        // Provides a singleton instance of AppDatabase
        @Provides
        @Singleton
        fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
            return AppDatabase.getDatabase(context, CoroutineScope(SupervisorJob()))
        }

        // Provides MedicationDao from AppDatabase
        @Provides
        fun provideMedicationDao(database: AppDatabase): MedicationDao {
            return database.medicationDao()
        }

        // Provides a singleton instance of MedicationRepository
        @Provides
        @Singleton
        fun provideMedicationRepository(medicationDao: MedicationDao): MedicationRepository {
            return MedicationRepository(medicationDao)
        }

        // Provides a singleton instance of FavoritesRepository
        @Provides
        @Singleton
        fun provideFavoritesRepository(
            auth: FirebaseAuth,
            firestore: FirebaseFirestore
        ): FavoritesRepository {
            return FavoritesRepository(auth, firestore)
        }
    }
}