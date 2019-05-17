package com.mibaldi.data.datasource

import com.mibaldi.domain.entity.Either
import com.mibaldi.domain.entity.MyFirebaseUser

interface LoginDataSource: BaseDataSource {
    suspend fun signIn(email:String,password:String) : Either<String,Boolean>
    suspend fun createAccount(email:String,password:String) : Either<String,Boolean>
    fun signOut()

    fun getCurrentUser() : MyFirebaseUser?
}