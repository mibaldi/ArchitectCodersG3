package com.mibaldi.domain.repository

import com.mibaldi.domain.entity.Either
import com.mibaldi.domain.entity.MyFirebaseUser

interface LoginRepository : BaseRepository {

    suspend fun signIn(email:String,password:String) : Either<String, Boolean>
    suspend fun createAccount(email:String,password:String): Either<String,Boolean>
    fun signOut()
    fun getCurrentUser() : MyFirebaseUser?
}