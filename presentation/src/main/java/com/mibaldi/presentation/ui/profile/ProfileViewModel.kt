package com.mibaldi.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mibaldi.domain.entity.MyFirebaseUser
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.data.model.toBarView
import com.mibaldi.presentation.ui.common.Scope
import com.mibaldi.domain.interactors.bar.GetBarInteractor
import com.mibaldi.domain.interactors.getCurrentUserInteractor.GetCurrentUserInteractor
import kotlinx.coroutines.launch

class ProfileViewModel(private val getCurrentUserInteractor: GetCurrentUserInteractor) : ViewModel(), Scope by Scope.Impl() {

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
            _model.value = UiModel.Content(myFirebaseUser)
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val myFirebaseUser: MyFirebaseUser?) : UiModel()
        class Navigation() : UiModel()
    }

}