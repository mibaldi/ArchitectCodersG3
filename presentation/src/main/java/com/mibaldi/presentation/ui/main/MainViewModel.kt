package com.mibaldi.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mibaldi.domain.interactors.bar.GetBarInteractor
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.data.model.toBarView
import com.mibaldi.presentation.ui.common.Navigator
import kotlinx.coroutines.launch

class MainViewModel(private val navigator: Navigator, private val barInteractor: GetBarInteractor) : ViewModel() {

    private val _items = MutableLiveData<List<BarView>>()
    val items: LiveData<List<BarView>>
        get() {
            if (_items.value == null) refresh()
            return _items
        }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading


    private fun refresh() {
        viewModelScope.launch {
            _dataLoading.value = true
            val results = barInteractor.getAllBars()
            _items.value = results.map { it.toBarView() }
            _dataLoading.value = false
        }
    }

    fun onBarClicked(bar: BarView) {
        navigator.goToDetail(bar)
    }

    fun goToProfile() {
        navigator.goToProfile()
    }

}