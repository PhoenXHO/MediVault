package com.ensas.medivault.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class FavoritesRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FavoritesRepositoryInterface {
    private val userId: String?
        get() = auth.currentUser?.uid

    override fun getFavorites(): Flow<List<Int>> = callbackFlow {
        val uid = userId ?: throw IllegalStateException("User is not logged in")

        val listener = firestore.collection("users")
            .document(uid)
            .collection("favorites")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    trySend(emptyList()) // Handle error as needed
                    return@addSnapshotListener
                }

                val favorites = snapshot?.documents?.mapNotNull { it.getLong("id")?.toInt() } ?: emptyList()
                trySend(favorites).isSuccess
            }

        awaitClose { listener.remove() }
    }

    override suspend fun addFavorite(medicationId: Int) {
        val uid = userId
        if (uid != null) {
            firestore.collection("users")
                .document(uid)
                .collection("favorites")
                .document(medicationId.toString())
                .set(mapOf("id" to medicationId))
        }
    }

    override suspend fun removeFavorite(medicationId: Int) {
        val uid = userId
        if (uid != null) {
            firestore.collection("users")
                .document(uid)
                .collection("favorites")
                .document(medicationId.toString())
                .delete()
        }
    }
}

class FakeFavoritesRepository : FavoritesRepositoryInterface {
    private val favorites = mutableSetOf(1, 2)

    override fun getFavorites(): Flow<List<Int>> = callbackFlow {
        trySend(favorites.toList()).isSuccess
        awaitClose { }
    }

    override suspend fun addFavorite(medicationId: Int) {
        favorites.add(medicationId)
    }

    override suspend fun removeFavorite(medicationId: Int) {
        favorites.remove(medicationId)
    }
}