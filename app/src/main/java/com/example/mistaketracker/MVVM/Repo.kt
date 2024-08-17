package com.example.mistaketracker.MVVM

import androidx.lifecycle.LiveData
import com.example.mistaketracker.Data.Mistake
import com.example.mistaketracker.Room.Dao
import javax.inject.Inject


class Repo @Inject constructor(var dao: Dao) {

    fun insert(mistake: Mistake)
    {
        dao.insert(mistake)
    }

    fun update(mistake: Mistake)
    {
        dao.update(mistake)
    }

    fun delete(mistake: Mistake)
    {
        dao.delete(mistake)
    }

    fun getAllMistakes():LiveData<List<Mistake>>
    {
       return dao.getAllMistakes()
    }

     fun getMistakebyId(Id:Long): Mistake
    {
        return dao.getMistakeById(Id)
    }
}