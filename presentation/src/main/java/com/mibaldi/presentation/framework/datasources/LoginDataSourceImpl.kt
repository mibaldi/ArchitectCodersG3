package com.mibaldi.presentation.framework.datasources

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.mibaldi.data.datasource.LoginDataSource
import arrow.core.Either
import com.mibaldi.domain.entity.MyFirebaseUser
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object LoginDataSourceImpl : LoginDataSource {


    private val TAG = LoginDataSourceImpl::class.java.canonicalName
    private val auth = FirebaseAuth.getInstance()
    override suspend fun signIn(email: String, password: String) =
        suspendCancellableCoroutine<Either<String, Boolean>> { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    update { Log.d(TAG, "signInWithEmail:success")
                        if (continuation.isActive) continuation.resume(Either.Right(true))      }
                }.addOnFailureListener{
                    Log.w(TAG, "signInWithEmail:failure", it)
                    if (continuation.isActive)
                        continuation.resume(Either.Left(it.message ?: "Error, Authentication failed."))

                }

        }

    private fun update(taskSuccess:()->Unit ){
        if (auth.currentUser?.photoUrl == null){
            val email = auth.currentUser?.email!!
            val index = email.indexOf('@')
            val nickname = email.substring(0, index)
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(nickname)
                .setPhotoUri(Uri.parse("http://lorempixel.com/400/200"))
                .build()

            auth.currentUser?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        taskSuccess()
                    }
                }
        } else {
            taskSuccess()
        }

    }

    override suspend fun createAccount(email: String, password: String) =
        suspendCancellableCoroutine<Either<String, Boolean>> { continuation ->
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                update { Log.d(TAG, "createUserWithEmail:success")
                    if (continuation.isActive) continuation.resume(Either.Right(true))      }
            }.addOnFailureListener {
                Log.w(TAG, "createUserWithEmail:failure", it)
                if (continuation.isActive)
                    continuation.resume(Either.Left(it.message ?: "Error, Authentication failed."))
            }
        }

    override fun signOut() {
        auth.signOut()

    }
    override fun getCurrentUser(): MyFirebaseUser? {
        return auth.currentUser?.let {
            with(it) {
                val email = email
                val name = displayName
                val photoUrl = photoUrl?.toString()
                val phone = phoneNumber
                MyFirebaseUser(email,name,photoUrl,phone)
            }
        }

    }

    override suspend fun removeAccount(): Either<String, Boolean> {
        val user = FirebaseAuth.getInstance().currentUser
       return suspendCancellableCoroutine { continuation ->
            user?.delete()?.addOnSuccessListener {
                    Log.d(TAG, "User account deleted.")
                    if (continuation.isActive) continuation.resume(Either.Right(true))
                }?.addOnFailureListener {
                    if (continuation.isActive)
                        continuation.resume(Either.Left(it.message ?: "Error, Remove account failed."))
                }
        }
    }
}