package com.mibaldi.domain.interactors.getCurrentUserInteractor

import com.mibaldi.domain.entity.MyFirebaseUser
import com.mibaldi.domain.repository.LoginRepository


class GetCurrentUserInteractorImpl(private val repository: LoginRepository) : GetCurrentUserInteractor {
    override fun currentUser(): MyFirebaseUser? {
       return repository.getCurrentUser()
    }

}