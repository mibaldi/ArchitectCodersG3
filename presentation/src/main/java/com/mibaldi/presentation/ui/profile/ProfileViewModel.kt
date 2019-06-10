package com.mibaldi.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mibaldi.domain.entity.MyFirebaseUser
import com.mibaldi.domain.interactors.account.RemoveAccountInteractor
import com.mibaldi.domain.interactors.login.SignOutInteractor
import com.mibaldi.domain.interactors.user.GetCurrentUserInteractor
import com.mibaldi.presentation.ui.common.Scope
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getCurrentUserInteractor: GetCurrentUserInteractor,
    private val signOutInteractor: SignOutInteractor,
    private val removeAccountInteractor: RemoveAccountInteractor
) : ViewModel(), Scope by Scope.Impl() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    init {
        initScope()
    }

    private fun refresh() {
        launch {
            _model.value = UiModel.Loading
            val myFirebaseUser = getCurrentUserInteractor.currentUser()
            if (myFirebaseUser != null) _model.value = UiModel.Content(myFirebaseUser)
            else _model.value = UiModel.Navigation
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    fun logout() {
        launch {
            signOutInteractor.signOut()
            _model.value = UiModel.Navigation
        }
    }

    fun removeAccount() {
        launch {
            _model.value = UiModel.Loading
            removeAccountInteractor.removeAccount().either(::handleFailure, ::handleSuccess)
        }
    }

    private fun handleSuccess(signInResult: Boolean) {
        _model.value = UiModel.Navigation
    }

    private fun handleFailure(error: String) {
        _model.value = UiModel.Error(error)
    }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val myFirebaseUser: MyFirebaseUser) : UiModel()
        class Error(val errorString: String) : UiModel()
        object Navigation : UiModel()
    }

}