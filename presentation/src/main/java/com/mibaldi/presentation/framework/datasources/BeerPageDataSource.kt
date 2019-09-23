package com.mibaldi.presentation.framework.datasources

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.mibaldi.domain.interactors.beer.BeerPagedInteractor
import com.mibaldi.presentation.data.model.BeerView
import com.mibaldi.presentation.data.model.toBeerView
import com.mibaldi.presentation.framework.api.brewery.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BeerPageDataSource(private val beerPagedInteractor: BeerPagedInteractor) :
    PageKeyedDataSource<Int, BeerView>(),
    CoroutineScope {

    companion object {
        const val INITIAL_PAGE = 1
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    var state: MutableLiveData<State> = MutableLiveData()



    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, BeerView>
    ) {
        updateState(State.LOADING)
        launch {
            beerPagedInteractor.getBeersByPage(INITIAL_PAGE).fold(
                { updateState(State.ERROR) },
                { result ->
                    updateState(State.DONE)
                    callback.onResult(
                        result.beers.map { it.toBeerView() },
                        null,
                        result.currentPage.inc()
                    )
                }
            )
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, BeerView>) {
        updateState(State.LOADING)
        launch {
            beerPagedInteractor.getBeersByPage(page = params.key).fold(
                { updateState(State.ERROR) },
                { result ->
                    updateState(State.DONE)
                    callback.onResult(
                        result.beers.map { it.toBeerView() },
                        result.currentPage.inc()
                    )
                }
            )
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, BeerView>
    ) {
        updateState(State.LOADING)
        launch {

            beerPagedInteractor.getBeersByPage(page = params.key).fold(
                { updateState(State.ERROR) },
                { result ->
                    updateState(State.DONE)
                    callback.onResult(
                        result.beers.map { it.toBeerView() },
                        result.currentPage.dec()
                    )
                }
            )
        }
    }


    private fun updateState(state: State) {
        this.state.postValue(state)
    }
}