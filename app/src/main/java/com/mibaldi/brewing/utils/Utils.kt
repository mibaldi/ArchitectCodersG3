package com.mibaldi.brewing.utils

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mibaldi.brewing.BuildConfig
import com.mibaldi.brewing.repositories.FirestoreDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.nio.charset.Charset


val Any?.safe get() = Unit


fun myLog(tag:String,msg:String){
    if (isDebugMode) Log.d(tag,msg)
}

val isDebugMode = BuildConfig.DEBUG


fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) {
    liveData.observe(this, Observer(body))
}

fun AppCompatActivity.loadJSONFromAsset(): String {
    var json: String? = null
    try {
        val inputStream = assets.open("mock1.json")
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

fun AppCompatActivity.initMock(){
    GlobalScope.launch {
        FirestoreDB.json = withContext(Dispatchers.IO) {loadJSONFromAsset()}
        FirestoreDB.addBars()
    }
}