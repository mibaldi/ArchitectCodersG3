package com.mibaldi.brewing.utils


interface DataListener<T> {

    fun onSuccess(data: T)

    fun onError(error: String)
}
