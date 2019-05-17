package com.mibaldi.domain.interactors.createAccountInteractor

import com.mibaldi.domain.entity.Either
import com.mibaldi.domain.repository.LoginRepository

class CreateAccountInteractorImpl(private val repository: LoginRepository) : CreateAccountInteractor {
     override suspend fun createAccount(email: String, password: String): Either<String, Boolean> {
         return repository.createAccount(email,password)

     }

}