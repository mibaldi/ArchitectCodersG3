package com.mibaldi.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mibaldi.domain.entity.MyFirebaseUser
import com.mibaldi.domain.interactors.account.RemoveAccountInteractor
import com.mibaldi.domain.interactors.login.SignOutInteractor
import com.mibaldi.domain.interactors.user.GetCurrentUserInteractor
import com.mibaldi.presentation.ui.common.Navigator
import com.mibaldi.presentation.ui.common.Scope
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val navigator: Navigator,
    private val getCurrentUserInteractor: GetCurrentUserInteractor,
    private val signOutInteractor: SignOutInteractor,
    private val removeAccountInteractor: RemoveAccountInteractor
) : ViewModel(), Scope by Scope.Impl() {

    private val _user = MutableLiveData<MyFirebaseUser?>()
    val user : LiveData<MyFirebaseUser?>
        get() {
            if (_user.value == null) refresh()
            return _user
        }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        initScope()
        refresh()
    }

    private fun refresh() {
        launch {
            _dataLoading.value = true
            val myFirebaseUser = getCurrentUserInteractor.currentUser()
            if (myFirebaseUser != null) {
                _user.value = myFirebaseUser
                _dataLoading.value = false
            }
            else navigator.goToLogin()
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    fun logout() {
        launch {
            signOutInteractor.signOut()
            navigator.goToLogin()
        }
    }

    fun removeAccount() {
        launch {
            _dataLoading.value = true
            removeAccountInteractor.removeAccount().either(::handleFailure, ::handleSuccess)
        }
    }

    private fun handleSuccess(signInResult: Boolean) {
        navigator.goToLogin()
    }

    private fun handleFailure(error: String) {
        _dataLoading.value = false
        _error.value = error
    }

}