package com.mibaldi.presentation.framework.datasources

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.mibaldi.data.datasource.FirestoreDataSource
import com.mibaldi.domain.entity.Bar
import com.mibaldi.domain.exception.RepositoryException
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FirestoreSimpleDataSource(private val firestoreApp: FirebaseFirestore) : FirestoreDataSource {
    override suspend fun getBarById(id: String): Either<RepositoryException, Bar> {
        return suspendCancellableCoroutine { continuation ->
            val db = firestoreApp
            db.collection("bars").document(id).get()
                .addOnSuccessListener {
                    continuation.resume(
                        it.toObject(Bar::class.java)?.right()
                            ?: RepositoryException.DataNotFoundException.left()
                    )
                }
                .addOnFailureListener {
                    continuation.resume(RepositoryException.NoConnectionException.left())
                }
        }
    }

    override suspend fun updateBar(bar: Bar) {
        return suspendCancellableCoroutine { continuation ->
            val db = firestoreApp
            val collection = db.collection("bars")
            collection.document(bar.id).set(bar)
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener {
                    continuation.resume(Unit)
                }
        }
    }

    override suspend fun getBarsOnce(): List<Bar> {
        return suspendCancellableCoroutine { continuation ->
            val db = firestoreApp
            val collection = db.collection("bars")
            collection.get()
                .addOnSuccessListener {
                    continuation.resume(it.toObjects())
                }
                .addOnFailureListener {
                    continuation.resume(emptyList())
                }
        }
    }

}