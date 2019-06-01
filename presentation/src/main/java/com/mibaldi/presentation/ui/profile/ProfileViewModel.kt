package com.mibaldi.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mibaldi.domain.entity.MyFirebaseUser
import com.mibaldi.presentation.ui.common.Scope
import com.mibaldi.domain.interactors.getCurrentUserInteractor.GetCurrentUserInteractor
import com.mibaldi.domain.interactors.signOutInteractor.SignOutInteractor
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getCurrentUserInteractor: GetCurrentUserInteractor,
    private val signOutInteractor: SignOutInteractor
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
        signOutInteractor.signOut()
        _model.value = UiModel.Navigation
    }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val myFirebaseUser: MyFirebaseUser) : UiModel()
        object Navigation : UiModel()
    }

}