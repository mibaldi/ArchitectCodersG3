package com.mibaldi.domain.interactors.user

import com.mibaldi.domain.entity.MyFirebaseUser
import com.mibaldi.domain.interactors.BaseInteractor
import com.mibaldi.domain.repository.LoginRepository

class GetCurrentUserInteractor(private val repository: LoginRepository) : BaseInteractor {
    suspend fun currentUser(): MyFirebaseUser? {
        return repository.getCurrentUser()
    }
}