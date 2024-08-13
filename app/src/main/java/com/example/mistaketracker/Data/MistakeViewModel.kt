package com.example.mistaketracker.Data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MistakeViewModel(var repo: Repo):ViewModel()
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