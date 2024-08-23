package com.example.mistaketracker.Retrofit

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//object RetrofitInstance {
//    private const val BASE_URL = "http://10.0.0.126:8091"
//
//    private val client = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val request = chain.request()
//            val jwtToken = "getJwtToken()" // Retrieve JWT token from storage
//            val newRequest = request.newBuilder()
//                .apply {
//                    jwtToken?.let {
//                        header("Authorization", "Bearer $it")
//                    }
//                }
//                .build()
//            chain.proceed(newRequest)
//        }
//        .build()
//
//    fun getRetrofitInstance(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//}
