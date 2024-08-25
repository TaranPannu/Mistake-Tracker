package com.example.mistaketracker.MVVM

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mistaketracker.DataClass.AuthResponse
import com.example.mistaketracker.DataClass.Login
import com.example.mistaketracker.DataClass.Mistake
import com.example.mistaketracker.DataClass.User
import com.example.mistaketracker.DataClass.UserDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class MistakeViewModel @Inject constructor(var repo: Repo):ViewModel()
{
    fun insert(mistake: Mistake)
    {
        viewModelScope.launch(Dispatchers.IO) {
        repo.insert(mistake)
    }}

    fun update(mistake: Mistake)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repo.update(mistake)
        }
    }

    fun delete(mistake: Mistake)
    {
        viewModelScope.launch(Dispatchers.IO) {
        repo.delete(mistake)
        }
    }

    fun getAllMistakes(): LiveData<List<Mistake>>
    {
        return repo.getAllMistakes()
    }

    fun getMistakebyId(Id:Long): Mistake
    {
return repo.getMistakebyId(Id)
    }

    fun getMistakebyTimeStamp(timestamp: Long):List<Mistake>
    {
        return repo.getMistakebyTimeStamp(timestamp)
    }

//    suspend fun getAllPosts():Response<List<Mistake>>
//    {
//        return repo.getAllPosts()
//    }
    suspend fun RegisterUser(user: User) :Response<AuthResponse>
    {
        return  repo.RegisterUser(user)
    }

//    suspend fun UpdateMistake(mistake: Mistake)
//    {
//        repo.UpdateMistake(mistake)
//    }
    suspend fun Loginuser(user: Login): Response<AuthResponse>
    {
        return repo.Loginuser(user)
    }

    fun saveJwtToken(token: String) {
       repo.saveJwtToken(token)
    }

    fun getJwtToken(): String? {

        return repo.getJwtToken()

    }

    suspend fun getDetails():Response<UserDetail>
    {
        return repo.getDetails()
    }
}