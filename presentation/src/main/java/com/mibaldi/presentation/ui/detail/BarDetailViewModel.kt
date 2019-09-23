package com.mibaldi.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.orNull
import com.mibaldi.domain.interactors.bar.GetBarInteractor
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.data.model.toBarView
import com.mibaldi.presentation.ui.common.Navigator
import kotlinx.coroutines.launch

class BarDetailViewModel constructor(
    private val navigator: Navigator,
    private val getBarInteractor: GetBarInteractor,
    private val barId: String
) :
    ViewModel() {


    private val _barView = MutableLiveData<BarView>()
    val barView: LiveData<BarView>
        get() {
            refreshData()
            return _barView
        }

    private fun refreshData() {
        if (_barView.value == null) {
            viewModelScope.launch {
                _barView.value = getBarInteractor.getBar(barId).orNull()?.toBarView()
            }
        }
    }

    fun clickAddBeer() {
        _barView.value?.let {
            navigator.showAddBeer(it)
            _barView.value = null
        }
    }

    fun refresh(it: BarView?) {
        _barView.value = it
    }
}