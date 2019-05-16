package com.mibaldi.brewing.interactors.SignInInteractor

import com.mibaldi.brewing.utils.Either

interface SignInInteractor {
    suspend fun signIn(email: String, password: String): Either<String, Boolean>
}