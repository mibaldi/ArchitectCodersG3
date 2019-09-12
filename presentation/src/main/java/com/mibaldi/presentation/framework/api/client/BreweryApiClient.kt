package com.mibaldi.presentation.framework.api.client

import com.mibaldi.presentation.BuildConfig

class BreweryApiClient(
    loggingInterceptor: LoggingInterceptor
) : RetrofitApiClient(loggingInterceptor) {

    override fun buildBaseUrl() = BuildConfig.URL_BREWERY

}