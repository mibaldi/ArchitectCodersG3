package com.mibaldi.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.data.model.toBarView
import com.mibaldi.presentation.ui.common.Scope
import com.mibaldi.domain.interactors.bar.GetBarInteractor
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
    }

    private fun refresh() {
        launch {
            _model.value = UiModel.Loading
            val results = barInteractor.getAllBars()
            _model.value = UiModel.Content(results.map { it.toBarView() })
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
    }

}