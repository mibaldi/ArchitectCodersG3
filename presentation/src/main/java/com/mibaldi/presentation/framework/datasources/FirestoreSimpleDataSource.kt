package com.mibaldi.presentation.framework.datasources

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.mibaldi.data.datasource.FirestoreDataSource
import com.mibaldi.domain.entity.Bar
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FirestoreSimpleDataSource(private val firestoreApp: FirebaseFirestore) : FirestoreDataSource {

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