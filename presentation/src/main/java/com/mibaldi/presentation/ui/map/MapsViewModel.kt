package com.mibaldi.presentation.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.mibaldi.data.repository.PermissionChecker
import com.mibaldi.domain.entity.Bar
import com.mibaldi.domain.interactors.bar.GetBarInteractor
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.data.model.toBarView
import com.mibaldi.presentation.ui.common.Scope
import kotlinx.coroutines.launch

class MapsViewModel(private val barInteractor: GetBarInteractor) : ViewModel(), Scope by Scope.Impl() {
    private val _model = MutableLiveData<UiModel>()
    private var results: List<Bar> = listOf()
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
            results = barInteractor.getAllBars()
            _model.value = UiModel.Content(results.map { it.toBarView() })
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    fun clickOnBar(position: LatLng?) {
        val bar = results.find {
            it.address.lat == position?.latitude
        }
        bar?.let {
            _model.value = UiModel.Footer(it.toBarView())
        }
    }

    fun onMapClick() {
        _model.value = UiModel.HideFooter
    }

    fun onFooterClicked(bar: BarView) {
        _model.value = UiModel.Navigation(bar)
    }

    sealed class UiModel {
        object Loading : UiModel()
        object HideFooter : UiModel()
        class Content(val bars: List<BarView>) : UiModel()
        class Footer(val bar: BarView): UiModel()
        class Navigation(val bar:BarView):UiModel()
    }
}
