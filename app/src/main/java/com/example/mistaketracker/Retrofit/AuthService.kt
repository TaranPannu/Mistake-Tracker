package com.example.mistaketracker.Retrofit

import com.example.mistaketracker.DataClass.AuthResponse
import com.example.mistaketracker.DataClass.Login
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/auth/login")
    fun login(@Body loginDto: Login): Call<AuthResponse>
}