package com.example.mistaketracker.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mistaketracker.Data.Mistake
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
}