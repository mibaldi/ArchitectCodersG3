package com.mibaldi.domain.interactors.login

import com.mibaldi.domain.interactors.BaseInteractor
import com.mibaldi.domain.repository.LoginRepository

class SignOutInteractor(private val repository: LoginRepository) : BaseInteractor {
    suspend fun signOut() {
        repository.signOut()
    }
}