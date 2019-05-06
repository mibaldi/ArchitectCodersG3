package com.mibaldi.brewing.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mibaldi.brewing.data.model.Beer
import com.mibaldi.brewing.interactors.BeerInteractor
import com.mibaldi.brewing.ui.common.Scope
import kotlinx.coroutines.launch

class MainViewModel(private val beerInteractor: BeerInteractor) : ViewModel(), Scope by Scope.Impl {

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
            val results = beerInteractor.getBeers()
            //val results = mutableListOf<Beer>()
            _model.value = UiModel.Content(results)
        }
    }

    fun onPubClicked(beer: Beer) {
        _model.value = UiModel.Navigation(beer)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val beers: List<Beer>) : UiModel()
        class Navigation(val beer: Beer) : UiModel()
    }

}