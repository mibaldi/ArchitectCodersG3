package com.mibaldi.brewing.interactors.SignOutInteractor

import com.mibaldi.brewing.repositories.LoginRepository

class SignOutInteractorImpl : SignOutInteractor {
    override fun signOut() {
        LoginRepository.signOut()
    }

}