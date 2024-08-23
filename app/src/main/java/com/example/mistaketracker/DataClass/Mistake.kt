package com.example.mistaketracker.DataClass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MistakeTable")
data class Mistake(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var title:String,
    var category:String,
    var count:String,
    var detail:String,
    var lesson:String,
    val img_path:String,
    var timestamp: Long
)
