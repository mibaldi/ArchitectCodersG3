package com.mibaldi.domain.interactors.account

import arrow.core.Either
import com.mibaldi.domain.interactors.BaseInteractor
import com.mibaldi.domain.repository.LoginRepository

class RemoveAccountInteractor(private val repository: LoginRepository) : BaseInteractor {
    suspend fun removeAccount(): Either<String, Boolean> {
        return repository.removeAccount()
    }
}