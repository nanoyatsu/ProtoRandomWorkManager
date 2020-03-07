package com.nanoyatsu.proto.randomWorkManager.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class History(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: String,
    val parent: String
)