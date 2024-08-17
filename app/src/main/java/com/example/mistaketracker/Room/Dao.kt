package com.example.mistaketracker.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mistaketracker.Data.Mistake

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Mistake)
    @Delete
    fun delete(note: Mistake)
    @Update
    fun update(note: Mistake)
    @Query("SELECT * FROM MistakeTable")
    fun getAllMistakes(): LiveData<List<Mistake>>

    @Query("SELECT * FROM MistakeTable WHERE id = :id")
     fun getMistakeById(id: Long): Mistake

    @Query("SELECT * FROM MistakeTable")     // we need it for testing
    fun getAllMistakesNonLiveData():List<Mistake>
}