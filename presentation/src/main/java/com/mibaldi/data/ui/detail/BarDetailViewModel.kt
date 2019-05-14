package com.mibaldi.data.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mibaldi.data.data.model.BarView

class BarDetailViewModel(private val barView: BarView) : ViewModel() {

    class UiModel(val barView: BarView)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) _model.value = UiModel(barView)
            return _model
        }
}