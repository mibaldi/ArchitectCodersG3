package com.mibaldi.domain.repository

import arrow.core.Either
import com.mibaldi.domain.entity.MyFirebaseUser

interface LoginRepository : BaseRepository {

    suspend fun signIn(email: String, password: String): Either<String, Boolean>
    suspend fun createAccount(email: String, password: String): Either<String, Boolean>
    suspend fun signOut()
    suspend fun getCurrentUser(): MyFirebaseUser?
    suspend fun removeAccount(): Either<String, Boolean>
}