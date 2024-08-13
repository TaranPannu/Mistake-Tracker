package com.example.mistaketracker.Data

import androidx.lifecycle.LiveData

class Repo(var dao: Dao) {

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