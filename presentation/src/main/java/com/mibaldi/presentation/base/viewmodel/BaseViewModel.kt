package com.mibaldi.presentation.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private val loading = MutableLiveData<Boolean>()
}