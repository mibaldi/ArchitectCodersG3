package com.mibaldi.presentation.framework.datasources

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.mibaldi.brewing.utils.DataListener
import com.mibaldi.data.datasource.LoginDataSource
import com.mibaldi.domain.entity.Either
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
                    Log.d(TAG, "signInWithEmail:success")
                    if (continuation.isActive)  continuation.resume(Either.Right(true))
                }.addOnFailureListener{
                    Log.w(TAG, "signInWithEmail:failure", it)
                    if (continuation.isActive)
                        continuation.resume(Either.Left(it.message ?: "Error, Authentication failed."))

                }

        }

    override suspend fun createAccount(email: String, password: String) =
        suspendCancellableCoroutine<Either<String, Boolean>> { continuation ->
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                Log.d(TAG, "createUserWithEmail:success")
                if (continuation.isActive) continuation.resume(Either.Right(true))
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

        return  if (auth.currentUser != null){
            val email = auth.currentUser?.email!!
            MyFirebaseUser(email)
        }else {
            null
        }


    }
}