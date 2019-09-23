package com.mibaldi.data.datasource

import arrow.core.Either
import com.mibaldi.domain.entity.MyFirebaseUser

interface LoginDataSource: BaseDataSource {
    suspend fun signIn(email:String,password:String) : Either<String,Boolean>
    suspend fun createAccount(email:String,password:String) : Either<String,Boolean>
    fun signOut()

    fun getCurrentUser() : MyFirebaseUser?
    suspend fun removeAccount(): Either<String, Boolean>
}