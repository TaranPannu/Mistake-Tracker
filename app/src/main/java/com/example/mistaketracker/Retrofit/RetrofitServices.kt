package com.example.mistaketracker.Retrofit


import com.example.mistaketracker.DataClass.AuthResponse
import com.example.mistaketracker.DataClass.Login
import com.example.mistaketracker.DataClass.Mistake
import com.example.mistaketracker.DataClass.UID
import com.example.mistaketracker.DataClass.User
import com.example.mistaketracker.DataClass.UserDetail
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface RetrofitServices {
//    @GET("/mistake")
//    suspend fun getAllPosts(): Response<List<Mistake>>
//
    @POST("/mistake")
    suspend fun InsertMistake(@Body mistake: Mistake): Response<UID>
//
//    @PUT("/mistake/update")
//    suspend fun UpdateMistake(@Body mistake: Mistake)

   @POST("/api/auth/register")
    suspend fun RegisterUser(@Body user: User): Response<AuthResponse>
    @POST("/api/auth/login")
    suspend fun Loginuser(@Body user: Login): Response<AuthResponse>

    @GET("details")
    suspend fun getDetails():Response<UserDetail>
}

