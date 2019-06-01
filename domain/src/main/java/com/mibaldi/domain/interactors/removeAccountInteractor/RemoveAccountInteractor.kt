package com.mibaldi.domain.interactors.removeAccountInteractor

import com.mibaldi.domain.entity.Either
import com.mibaldi.domain.repository.LoginRepository

class RemoveAccountInteractor(private val repository: LoginRepository) {
    suspend fun removeAccount(): Either<String, Boolean> {
        return repository.removeAccount()
    }
}