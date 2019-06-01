package com.mibaldi.data.repository

import com.mibaldi.data.datasource.LoginDataSource
import com.mibaldi.domain.entity.Either
import com.mibaldi.domain.entity.MyFirebaseUser
import com.mibaldi.domain.repository.LoginRepository

class LoginRepositoryImpl(private val loginDataSource: LoginDataSource) : LoginRepository {



    override suspend fun signIn(email: String, password: String)  =
        loginDataSource.signIn(email,password)

    override suspend fun createAccount(email: String, password: String) =
        loginDataSource.createAccount(email,password)


    override fun signOut() {
        loginDataSource.signOut()
    }
    override fun getCurrentUser(): MyFirebaseUser? {
        return loginDataSource.getCurrentUser()
    }
    override suspend fun removeAccount(): Either<String, Boolean> {
        return loginDataSource.removeAccount()
    }


}