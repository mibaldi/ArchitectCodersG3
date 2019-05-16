package com.mibaldi.brewing.interactors.SignInInteractor

import com.mibaldi.brewing.repositories.LoginRepository
import com.mibaldi.brewing.utils.DataListener
import com.mibaldi.brewing.utils.Either
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class SignInInteractorImpl :SignInInteractor {
    override suspend fun signIn(email: String,password:String) =
        suspendCancellableCoroutine<Either<String,Boolean>> {continuation ->
            LoginRepository.signIn(email,password,object : DataListener<Boolean>{
                override fun onSuccess(data: Boolean) {
                    continuation.resume(Either.Right(data))
                }

                override fun onError(error: String) {
                    if (continuation.isActive) {
                        continuation.resume(Either.Left(error))
                    }
                }
            })
    }

}