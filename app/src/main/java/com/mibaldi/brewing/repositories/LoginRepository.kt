package com.mibaldi.brewing.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.mibaldi.brewing.utils.DataListener

object LoginRepository {
    private val TAG = LoginRepository::class.java.canonicalName
    private val auth = FirebaseAuth.getInstance()

    fun signIn(email:String,password:String,listener: DataListener<Boolean>){
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d(TAG, "signInWithEmail:success")
                listener.onSuccess(true)
            }.addOnFailureListener{
                Log.w(TAG, "signInWithEmail:failure", it)
                listener.onError(it.message ?: "Error, Authentication failed.")
            }
    }
    fun createAccount(email:String,password:String,listener: DataListener<Boolean>){
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            Log.d(TAG, "createUserWithEmail:success")
            listener.onSuccess(true)
        }.addOnFailureListener {
            Log.w(TAG, "createUserWithEmail:failure", it)
            listener.onError(it.message ?: "Error, Authentication failed.")
        }
    }
    fun signOut(){
        auth.signOut()
    }

    fun getCurrentUser() = auth.currentUser


}