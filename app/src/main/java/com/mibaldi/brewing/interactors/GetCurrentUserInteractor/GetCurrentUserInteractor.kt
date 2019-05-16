package com.mibaldi.brewing.interactors.GetCurrentUserInteractor

import com.google.firebase.auth.FirebaseUser

interface GetCurrentUserInteractor {
     fun currentUser(): FirebaseUser?
}