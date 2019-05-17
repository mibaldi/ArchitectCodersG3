package com.mibaldi.domain.interactors.signOutInteractor

import com.mibaldi.domain.repository.LoginRepository

class SignOutInteractorImpl(private val repository: LoginRepository) : SignOutInteractor {
    override  fun signOut() {
        repository.signOut()
    }

}