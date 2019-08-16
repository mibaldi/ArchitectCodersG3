package com.mibaldi.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mibaldi.presentation.data.model.BarView

class BarDetailViewModel : ViewModel() {

    private val _data = MutableLiveData<BarView>()
    val data: LiveData<BarView>
        get() {
            return _data
        }

    fun setData(barView: BarView?) {
        _data.value = barView
    }
}