package com.mibaldi.brewing.repositories

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mibaldi.brewing.data.entities.firestore.Bar
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.DocumentReference
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
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

    private fun generateMock():List<Bar>{


        val bar = Bar(
            null,
            "Paddy's Bar Irlandés",
            "https://lh5.googleusercontent.com/p/AF1QipPGQboGJ-jQ9l3kvOFJKYvBHaUxByFpbX2XPExg=w494-h240-k-no",
            "Paddy's Bar Irlandés",
            "Irish Pub",
            8,
            BarLocation(40.4519478, -3.6780381, "Madrid"),
            "paddys.es",
            "915 62 26 54"
        )
        val bar2 = Bar(
            null,
            "Zeus Bar",
            "https://lh5.googleusercontent.com/p/AF1QipM3o7lH8e3SlmfznwOPGT0bvp9OafxEqQMgwHMf=w425-h240-k-no",
            "Bar restaurante",
            "Bar restaurante",
            8,

            BarLocation(40.4519886,-3.692506, "Madrid"),
            "zeusbar.com",
            "915 56 16 81"
        )
        //return listOf(bar,bar2)

      return mockFromJson(json)


    }

    fun mockFromJson(json:String): List<Bar> {
        return try {
            val listType = object : TypeToken<List<Bar>>() {}.type
            Gson().fromJson<List<Bar>>(json, listType)
        } catch (e: Exception){
            mutableListOf()
        }
    }

    fun loadJSONFromAsset(activity: AppCompatActivity): String {
        var json: String? = null
        try {
            val inputStream = activity.assets.open("mock1.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.defaultCharset())
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }

        return json
    }
}