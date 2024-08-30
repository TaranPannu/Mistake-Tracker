package com.example.mistaketracker.Services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.mistaketracker.DataClass.Mistake
import com.example.mistaketracker.DataClass.User
import com.example.mistaketracker.MVVM.MistakeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DataSyncService : Service() {
    @Inject
    lateinit var mistakeViewModel: MistakeViewModel

    // Create a CoroutineScope tied to the Service's lifecycle
    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())
    override fun onCreate() {
        super.onCreate()
        Log.d("s2", "Service Created")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("s2", "onStartCommand")

        val timestamp = System.currentTimeMillis();
        val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val prev_timestamp = sharedPreferences.getLong("Time_Stamp", 0L)
        val editor = sharedPreferences.edit()
        editor.putLong("Time_Stamp", timestamp)
        editor.apply()

        serviceScope.launch {
            try {
                val list = mistakeViewModel.getMistakebyTimeStamp(prev_timestamp)
                Log.d("s2", "Done--" + list)
                list.forEach { it ->
                    if (it.uid == 0) {
                        val x = mistakeViewModel.InsertMistake(it)
                        it.uid = x.body()?.uid
                        mistakeViewModel.update(it)
                    } else {
                        mistakeViewModel.InsertMistake(it)
                    }
                    Log.d("s2", "Done--" + it)

                }
            } catch (e: Exception) {
                Log.e("s2", "Error fetching data", e)
            } finally {
                Log.d("s2", "end--")
                stopSelf(startId) // stop the service after the task is complete
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("s2", "Service Stop")
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

}