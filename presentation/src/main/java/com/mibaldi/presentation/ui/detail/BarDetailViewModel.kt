package com.mibaldi.presentation.ui.detail

import androidx.lifecycle.ViewModel
import com.mibaldi.presentation.data.model.BarView
import com.mibaldi.presentation.ui.common.Navigator

class BarDetailViewModel constructor(private val navigator: Navigator, val barView: BarView?) :
    ViewModel() {


    fun clickAddBeer() {
        barView?.let {
            navigator.showAddBeer(it)
        }
    }
}