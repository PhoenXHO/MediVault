package com.ensas.medivault.di

import android.content.Context
import com.ensas.medivault.data.UserPreferences
import com.ensas.medivault.data.dao.MedicationDao
import com.ensas.medivault.data.database.AppDatabase
import com.ensas.medivault.data.repository.MedicationRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

// To provide the app dependencies using dependency injection (DI) with Dagger Hilt
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	@Provides
	@Singleton
	fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
		return UserPreferences(context)
	}

	@Provides
	@Singleton
	fun provideFirebaseAuth(): FirebaseAuth {
		return FirebaseAuth.getInstance()
	}

	@Provides
	@Singleton
	fun provideCoroutineScope(): CoroutineScope {
		return CoroutineScope(SupervisorJob())
	}


	@Provides
	@Singleton
	fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
		return AppDatabase.getDatabase(context, CoroutineScope(SupervisorJob()))
	}

	@Provides
	fun provideMedicationDao(database: AppDatabase): MedicationDao {
		return database.medicationDao()
	}

	@Provides
	@Singleton
	fun provideMedicationRepository(medicationDao: MedicationDao): MedicationRepository {
		return MedicationRepository(medicationDao)
	}
}