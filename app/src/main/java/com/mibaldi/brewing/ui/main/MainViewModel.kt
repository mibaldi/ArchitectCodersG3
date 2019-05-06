package com.mibaldi.brewing.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mibaldi.brewing.ui.common.Scope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(), Scope by Scope.Impl {

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
            //val results = pubsRepository.findPopulaPubs().results
            val results = mutableListOf<String>()
            _model.value = UiModel.Content(results)
        }
    }

    fun onPubClicked(pub: String) {
        _model.value = UiModel.Navigation(pub)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val pubs: List<String>) : UiModel()
        class Navigation(val pub: String) : UiModel()
    }

}