package com.example.mistaketracker.Data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MistakeViewModelFactory (private val repo: Repo): ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MistakeViewModel(repo) as T
    }
}