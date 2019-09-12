package com.mibaldi.domain.interactors.login

import arrow.core.Either
import com.mibaldi.domain.interactors.BaseInteractor
import com.mibaldi.domain.repository.LoginRepository


class SignInInteractor(private val repository: LoginRepository) : BaseInteractor {

    suspend fun signIn(email: String, password: String): Either<String, Boolean> {
        return repository.signIn(email, password)
    }

}