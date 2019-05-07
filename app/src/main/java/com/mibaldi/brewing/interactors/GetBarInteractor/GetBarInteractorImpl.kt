package com.mibaldi.brewing.interactors.GetBarInteractor

import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QuerySnapshot
import com.mibaldi.brewing.data.entities.firestore.Bar
import com.mibaldi.brewing.data.entities.firestore.toBarView
import com.mibaldi.brewing.data.model.BarView
import com.mibaldi.brewing.repositories.FirestoreDB
import kotlinx.coroutines.suspendCancellableCoroutine
import java.lang.Exception
import kotlin.coroutines.resume

class GetBarInteractorImpl() :GetBarInteractor{

    override suspend fun getAllBars() : List<BarView>{
        return suspendCancellableCoroutine { continuation ->
            FirestoreDB.getBarsOnce(OnSuccessListener<QuerySnapshot> {
                val listOfBarView = mutableListOf<BarView>()
                for (document in it){
                    val bar = document.toObject(Bar::class.java)
                    listOfBarView.add(bar.toBarView())
                }
                continuation.resume(listOfBarView)
            }, OnFailureListener {
                Log.e("GETALLBARS","ERROR",it)
                continuation.resume(emptyList())
            })
        }
    }


}