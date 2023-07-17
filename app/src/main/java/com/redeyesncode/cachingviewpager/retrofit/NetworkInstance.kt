package com.redeyesncode.cachingviewpager.retrofit

import com.redeyesncode.cachingviewpager.base.Constants.BASE_URL_APP
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkInstance {
    companion object {

        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logging).build()

            Retrofit.Builder().baseUrl(BASE_URL_APP)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client).build()

        }
        val api: AppEndPoint by lazy {
            retrofit.create(AppEndPoint::class.java)
        }
    }
}