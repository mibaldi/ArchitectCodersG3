package com.mibaldi.presentation.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.mibaldi.domain.entity.Bar
import com.mibaldi.domain.interactors.bar.GetBarInteractor
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.data.model.toBarView
import com.mibaldi.presentation.ui.common.Navigator
import kotlinx.coroutines.launch

class MapsViewModel(private val navigator: Navigator,private val barInteractor: GetBarInteractor) : ViewModel() {
    private var results: List<Bar> = listOf()

    private val _bars = MutableLiveData<List<BarView>>()
    val bars: LiveData<List<BarView>>
        get() {
            if (_bars.value == null) refresh()
            return _bars
        }
    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _footer = MutableLiveData<BarView?>()
    val footer: LiveData<BarView?>
        get() = _footer


    private fun refresh() {
        viewModelScope.launch {
            _dataLoading.value = true
            results = barInteractor.getAllBars()
            _bars.value = results.map { it.toBarView() }
            _dataLoading.value = false
        }
    }

    fun clickOnBar(position: LatLng?) {
        val bar = results.find {
            it.address.lat == position?.latitude
        }
        bar?.let {
            _footer.value = bar.toBarView()
        }
    }

    fun onMapClick() {
        _footer.value = null
    }

    fun onFooterClicked(bar: BarView) {
        navigator.goToDetail(bar.id)
    }
}
