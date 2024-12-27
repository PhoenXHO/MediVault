package com.ensas.medivault.di

import com.ensas.medivault.data.repository.MedicationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// To provide the app dependencies using dependency injection (DI) with Dagger Hilt
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMedicationRepository(): MedicationRepository {
        return MedicationRepository()
    }
}