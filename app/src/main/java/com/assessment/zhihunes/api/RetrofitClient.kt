package com.assessment.zhihunes.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * description ： 网络访问
 * author : HI-IR
 * email : qq2420226433@outlook.com
 * date : 2025/5/1 20:13
 */
object RetrofitClient {
    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(300, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder().baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}