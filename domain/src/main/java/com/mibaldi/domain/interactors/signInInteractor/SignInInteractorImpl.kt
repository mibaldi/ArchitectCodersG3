package com.mibaldi.domain.interactors.signInInteractor


import com.mibaldi.domain.entity.Either
import com.mibaldi.domain.repository.LoginRepository

class SignInInteractorImpl(private val repository: LoginRepository) :SignInInteractor {
    override suspend fun signIn(email: String,password:String): Either<String, Boolean> {
        return repository.signIn(email,password)
    }

}