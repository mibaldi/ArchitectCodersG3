package com.mibaldi.presentation.framework.api.client

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

abstract class RetrofitApiClient(
    private val loggingInterceptor: LoggingInterceptor
) {

    private val retrofit: Retrofit by lazy { buildRetrofit() }

    private fun buildRetrofit(): Retrofit {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)

        val builder = Retrofit.Builder()
            .baseUrl(buildBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())

        return builder.build()
    }

    abstract fun buildBaseUrl(): String

    fun <T> generatedApi(service: Class<T>): T = retrofit.create(service)

}