package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork

import com.google.android.datatransport.BuildConfig
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.apiConstants.ApiConstantsShabdjal
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClientShabdjal {


    private var apiService: ApiServiceShabdjal? = null

    fun getInstance(): ApiServiceShabdjal? {
        if (apiService != null) return apiService
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) interceptor.setLevel(HttpLoggingInterceptor.Level.BODY) else interceptor.setLevel(
            HttpLoggingInterceptor.Level.NONE
        )
        val builder = OkHttpClient.Builder()
        builder.readTimeout(5, TimeUnit.MINUTES)
            .writeTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(5, TimeUnit.MINUTES)
        val okHttpClient: OkHttpClient = builder
            .addInterceptor(interceptor).addInterceptor(Interceptor { chain ->
                val request =
                    chain.request().newBuilder().addHeader("content-type", "application/json")
                        .build()
                chain.proceed(request)
            })
            .build()
        val rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.computation())
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ApiConstantsShabdjal.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(rxAdapter)
            .client(okHttpClient)
            .build()
        apiService = retrofit.create(ApiServiceShabdjal::class.java)
        return apiService
    }
}