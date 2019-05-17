package com.mibaldi.domain.interactors.createAccountInteractor

import com.mibaldi.domain.entity.Either
import com.mibaldi.domain.interactors.BaseInteractor


interface CreateAccountInteractor: BaseInteractor {
    suspend fun createAccount(email: String,password:String): Either<String, Boolean>
}