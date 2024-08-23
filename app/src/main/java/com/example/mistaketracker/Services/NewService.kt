package com.example.mistaketracker.Services


import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.media.MediaPlayer
import android.provider.Settings
import android.util.Log

class NewService: Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("NewService", "Service Created")
    }


    // declaring object of MediaPlayer
    private lateinit var player:MediaPlayer

    // execution of service will start
    // on calling this method
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("NewService", "onStartCommand")

        // creating a media player which
        // will play the audio of Default
        // ringtone in android device
        player = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI)

        // providing the boolean
        // value as true to play
        // the audio on loop
        player.setLooping(true)

        // starting the process
        player.start()

        // returns the status
        // of the program
        return START_STICKY
    }

    // execution of the service will
    // stop on calling this method
    override fun onDestroy() {
        super.onDestroy()
        Log.d("NewService", "Service Stop")
        // stopping the process
        player.stop()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}