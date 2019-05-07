package com.mibaldi.brewing.repositories

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mibaldi.brewing.data.entities.firestore.Bar
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.*
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import com.mibaldi.brewing.data.entities.firestore.BarLocation
import java.io.IOException
import java.lang.Exception
import java.nio.charset.Charset


object FirestoreDB {

    var json : String = ""
    fun addBars(){
        val db = FirebaseFirestore.getInstance()
        val generateMock = generateMock()
        val collection = db.collection("bars")
        generateMock.forEach {
            collection.add(it)
                .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                    val idMap = mapOf(Pair("id",documentReference.id))
                    documentReference.update(idMap)
                    Log.d("addBars","success ${documentReference.id}")
                })
                .addOnFailureListener(OnFailureListener { e ->
                    Log.e("addBars","Error",e)
                })
        }
    }

    fun getBarsOnce(onSuccessListener: OnSuccessListener<QuerySnapshot>,onFailureListener: OnFailureListener){
        val db = FirebaseFirestore.getInstance()
        val collection = db.collection("bars")
        collection.get()
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }
    fun getBarsRealTime(){
        val db = FirebaseFirestore.getInstance()
        val collection = db.collection("bars")
        collection.addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
                if (e != null) {
                    Log.w("", "Listen failed.", e)
                    return@EventListener
                }

                val cities = ArrayList<String>()
                for (doc in value!!) {
                    if (doc.get("name") != null) {
                        cities.add(doc.getString("name")!!)
                    }
                }
                Log.d("", "Current cites in CA: $cities")
            })
    }

    private fun generateMock():List<Bar>{
      return mockFromJson(json)
    }

    private fun mockFromJson(json:String): List<Bar> {
        return try {
            val listType = object : TypeToken<List<Bar>>() {}.type
            Gson().fromJson<List<Bar>>(json, listType)
        } catch (e: Exception){
            mutableListOf()
        }
    }


}