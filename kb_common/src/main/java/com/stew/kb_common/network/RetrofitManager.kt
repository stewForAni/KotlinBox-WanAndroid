package com.stew.kb_common.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by stew on 5/20/22.
 * mail: stewforani@gmail.com
 */
object RetrofitManager {

    private const val BASE_URL = "https://www.wanandroid.com/"

    private var retrofit: Retrofit

    init {

        val logInterceptor = HttpLoggingInterceptor {
            Log.d("OkHttp Log : ", it)
        }.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient().newBuilder()
            .callTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .followRedirects(false)
            .cookieJar(MyCookieJar())
            .addInterceptor(logInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun <T> getService(service: Class<T>): T {
        return retrofit.create(service)
    }
}