package com.mibaldi.presentation.datasources

import com.google.firebase.firestore.FirebaseFirestore
import com.mibaldi.data.datasource.FirestoreDataSource
import com.mibaldi.domain.entity.Bar
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class FirestoreSimpleDataSource(private val firestoreApp: FirebaseFirestore) : FirestoreDataSource {
    override suspend fun addBars(bars: List<Bar>) {
    }

    override suspend fun getBarsOnce(): List<Bar> {
        return suspendCancellableCoroutine { continuation ->
            val db = firestoreApp
            val collection = db.collection("bars")
            collection.get()
                .addOnSuccessListener {
                    val listOfBarView = mutableListOf<Bar>()
                    for (document in it) {

                        val bar = document.toObject(Bar::class.java)
                        listOfBarView.add(bar)
                    }
                    continuation.resume(listOfBarView)
                }
                .addOnFailureListener {
                    continuation.resume(emptyList())
                }
        }
    }

    override suspend fun getBarsRealTime() {

    }
}