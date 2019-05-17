package com.mibaldi.domain.interactors.signInInteractor

import com.mibaldi.domain.entity.Either
import com.mibaldi.domain.interactors.BaseInteractor


interface SignInInteractor : BaseInteractor {
    suspend fun signIn(email: String, password: String): Either<String, Boolean>
}