package com.example.mistaketracker.MVVM

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.example.mistaketracker.DataClass.AuthResponse
import com.example.mistaketracker.DataClass.Login
import com.example.mistaketracker.DataClass.Mistake
import com.example.mistaketracker.DataClass.UID
import com.example.mistaketracker.DataClass.User
import com.example.mistaketracker.DataClass.UserDetail
import com.example.mistaketracker.Retrofit.RetrofitServices
import com.example.mistaketracker.Room.Dao
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject


class Repo @Inject constructor(
    var dao: Dao,
    val retrofitServices: RetrofitServices,
    private val sharedPreferences: SharedPreferences
) {

    fun insert(mistake: Mistake) {
        dao.insert(mistake)
    }

    fun update(mistake: Mistake) {
        dao.update(mistake)
    }

    fun delete(mistake: Mistake) {
        dao.delete(mistake)
    }

    fun getAllMistakes(): LiveData<List<Mistake>> {
        return dao.getAllMistakes()
    }

    fun getMistakebyId(Id: Long): Mistake {
        return dao.getMistakeById(Id)
    }

    fun getMistakebyTimeStamp(timestamp: Long): List<Mistake> {
        return dao.getMistakebyTimeStamp(timestamp)
    }

//    suspend fun getAllPosts(): Response<List<Mistake>> {
//        return retrofitServices.getAllPosts()
//    }

    suspend fun RegisterUser(user: User): Response<AuthResponse> {
        return retrofitServices.RegisterUser(user)
    }

//    suspend fun UpdateMistake(mistake: Mistake) {
//        retrofitServices.UpdateMistake(mistake)
//    }

    suspend fun Loginuser(user: Login): Response<AuthResponse> {
        return retrofitServices.Loginuser(user)
    }

    fun saveJwtToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("jwt_token", token)
        editor.apply()
    }

    fun getJwtToken(): String? {
        return sharedPreferences.getString("jwt_token", null)
    }

    suspend fun getDetails(): Response<UserDetail> {
        return retrofitServices.getDetails()
    }

    suspend fun InsertMistake(@Body mistake: Mistake): Response<UID>
    {
        return retrofitServices.InsertMistake(mistake)
    }
}