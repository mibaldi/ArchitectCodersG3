package com.mibaldi.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mibaldi.presentation.base.viewmodel.BaseViewModel
import com.mibaldi.presentation.data.model.BarView

class BarDetailViewModel(private val barView: BarView) : BaseViewModel() {

    private val _data = MutableLiveData<BarView>()
    val data: LiveData<BarView>
        get() {
            if (_data.value == null) _data.value = barView
            return _data
        }
}