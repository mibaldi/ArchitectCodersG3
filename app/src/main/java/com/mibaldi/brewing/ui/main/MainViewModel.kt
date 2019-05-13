package com.mibaldi.brewing.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.mibaldi.brewing.data.model.BarView
import com.mibaldi.brewing.data.model.Beer
import com.mibaldi.brewing.interactors.BeerInteractor
import com.mibaldi.brewing.interactors.GetBarInteractor.GetBarInteractor
import com.mibaldi.brewing.interactors.GetBarInteractor.GetBarInteractorImpl
import com.mibaldi.brewing.ui.common.Scope
import kotlinx.coroutines.launch

class MainViewModel(private val barInteractor: GetBarInteractor) : ViewModel(), Scope by Scope.Impl {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }
    init {
        initScope()
        _model.value = UiModel.CurrentUser(FirebaseAuth.getInstance().currentUser?.email.toString())
    }

    private fun refresh() {
        launch {
            _model.value = UiModel.Loading
            val results = barInteractor.getAllBars()
            _model.value = UiModel.Content(results)
        }
    }

    fun onBarClicked(bar: BarView) {
        _model.value = UiModel.Navigation(bar)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val bars: List<BarView>) : UiModel()
        class Navigation(val bar: BarView) : UiModel()
        class CurrentUser(val user: String) : UiModel()
    }

}