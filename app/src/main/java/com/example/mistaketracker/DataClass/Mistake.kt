package com.example.mistaketracker.DataClass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MistakeTable")
data class Mistake(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L, // Local Room ID
    var uid: Int? = 0, // Server UID
    var title:String,
    var category:String,
    var count:String,
    var detail:String,
    var lesson:String,
    val img_path:String,
    var timestamp: Long,
    val share: Boolean = false
)
