package com.mibaldi.brewing.interactors.GetCurrentUserInteractor

import com.google.firebase.auth.FirebaseUser
import com.mibaldi.brewing.repositories.LoginRepository

class GetCurrentUserInteractorImpl : GetCurrentUserInteractor {
    override fun currentUser(): FirebaseUser? {
       return LoginRepository.getCurrentUser()
    }

}