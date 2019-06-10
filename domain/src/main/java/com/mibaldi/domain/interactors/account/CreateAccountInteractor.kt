package com.mibaldi.domain.interactors.account

import com.mibaldi.domain.entity.Either
import com.mibaldi.domain.interactors.BaseInteractor
import com.mibaldi.domain.repository.LoginRepository


class CreateAccountInteractor constructor(private val repository: LoginRepository) : BaseInteractor {
    suspend fun createAccount(email: String, password: String): Either<String, Boolean> {
        return repository.createAccount(email, password)

    }
}