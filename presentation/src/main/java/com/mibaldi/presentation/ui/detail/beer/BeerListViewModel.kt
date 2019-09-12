package com.mibaldi.presentation.ui.detail.beer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mibaldi.presentation.data.model.BeerView
import com.mibaldi.presentation.framework.datasources.BeerPageDataSource

class BeerListViewModel(private val dataSource: BeerPageDataSource) : ViewModel() {
    private var postsLiveData: LiveData<PagedList<BeerView>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData = initializedPagedListBuilder(config).build()
    }

    val post: LiveData<PagedList<BeerView>> = postsLiveData
    val visibility = dataSource.state

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, BeerView> {

        val dataSourceFactory = object : DataSource.Factory<Int, BeerView>() {
            override fun create(): DataSource<Int, BeerView> {
                return dataSource
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

}