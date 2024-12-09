package com.example.siassessment.data.remote.services

import com.example.siassessment.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ConsumerServiceFactory {

    private const val TIME_OUT = 120L

    fun makeRetrofitService(
    ): Retrofit {

        val okHttpClient = makeOkHttpClient()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun makeOkHttpClient(
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .hostnameVerifier { _, _ -> true }
            .build()
    }

}