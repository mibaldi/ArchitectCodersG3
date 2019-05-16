package com.mibaldi.brewing.interactors.CreateAccountInteractor

import com.mibaldi.brewing.utils.Either

interface CreateAccountInteractor {
    suspend fun createAccount(email: String,password:String): Either<String, Boolean>
}