package com.mibaldi.domain.interactors.getCurrentUserInteractor

import com.mibaldi.domain.entity.MyFirebaseUser
import com.mibaldi.domain.interactors.BaseInteractor

interface GetCurrentUserInteractor: BaseInteractor {
     fun currentUser(): MyFirebaseUser?
}