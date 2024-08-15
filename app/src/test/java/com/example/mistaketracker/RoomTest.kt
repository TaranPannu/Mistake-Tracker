package com.example.mistaketracker

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mistaketracker.Data.Dao
import com.example.mistaketracker.Data.Mistake
import com.example.mistaketracker.Data.MistakeDatabase
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) // Engine that drive all the test cases
class RoomTest {

        private lateinit var database: MistakeDatabase
        private lateinit var dao: Dao
        @Before
        fun setUp()
        {
            val context = ApplicationProvider.getApplicationContext<Context>()
            database = Room.inMemoryDatabaseBuilder(context,MistakeDatabase::class.java).build()
            dao = database.getMistakeDao()
        }
        // Will test adding and fetching Note
        @Test
        fun add_mistake_to_database() = runBlocking(Dispatchers.IO) {// we are accessing database so we need background thread
            val mistake =Mistake(9997392, "dd","ss","1","ww","","")
            dao.insert(mistake)
            val mistakes = dao.getAllMistakesNonLiveData()
            TestCase.assertTrue(mistakes!!.contains(mistake))
        }
        @Test
        fun delete_from_database() = runBlocking(Dispatchers.IO) {
            val mistake =Mistake(99973892, "dd","ss","1","ww","","")
            dao.insert(mistake)
            dao.delete(mistake)
            val mistakes = dao.getAllMistakesNonLiveData()
            TestCase.assertTrue(!mistakes!!.contains(mistake))
        }
        @After
        fun tearDown() // To release the used memory
        {
            database.close()
        }
}