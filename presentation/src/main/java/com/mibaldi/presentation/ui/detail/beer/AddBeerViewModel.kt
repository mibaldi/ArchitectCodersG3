package com.mibaldi.presentation.ui.detail.beer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mibaldi.domain.interactors.bar.UpdateBarInteractor
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.data.model.BeerView
import com.mibaldi.presentation.data.model.toBar
import com.mibaldi.presentation.utils.Event
import kotlinx.coroutines.launch

class AddBeerViewModel(
    private val barView: BarView?,
    private val updateBarInteractor: UpdateBarInteractor
) : ViewModel() {

    private val _close = MutableLiveData<Event<Unit>>()
    val close: LiveData<Event<Unit>>
        get() = _close

    private val _navigateToBeerList = MutableLiveData<Event<Unit>>()
    val navigateToBeerList: LiveData<Event<Unit>>
        get() = _navigateToBeerList

    private val _accept = MutableLiveData<Event<Unit>>()
    val accept: LiveData<Event<Unit>>
        get() = _accept

    val observations = MutableLiveData<String>()

    private val _data = MutableLiveData<BeerView>()

    val rating = MutableLiveData<Float>()
    val beerView: LiveData<BeerView>
        get() {
            return _data
        }

    fun setData(barView: BeerView) {
        _data.value = barView
    }

    fun cancelClick() {
        _close.value = Event(Unit)
    }

    fun acceptClick() {
        val beerView = beerView.value
        beerView?.let {
            it.observation = observations.value
            it.rating = rating.value ?: 0f
            barView?.beers?.add(it)
        }

        viewModelScope.launch {
            barView?.toBar()?.let { updateBarInteractor.updateBar(it) }
        }
        _accept.value = Event(Unit)
    }

    fun navigateToBeerListClick() {
        _navigateToBeerList.value = Event(Unit)
    }

}