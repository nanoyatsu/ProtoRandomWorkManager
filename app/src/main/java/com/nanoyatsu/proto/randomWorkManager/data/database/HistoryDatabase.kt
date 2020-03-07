package com.nanoyatsu.proto.randomWorkManager.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nanoyatsu.proto.randomWorkManager.MyApplication
import com.nanoyatsu.proto.randomWorkManager.data.database.dao.HistoryDao
import com.nanoyatsu.proto.randomWorkManager.data.database.entity.History

@Database(entities = [History::class], version = 1, exportSchema = true)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        private var INSTANCE: HistoryDatabase? = null

        @Synchronized
        fun getInstance(): HistoryDatabase {
            if (INSTANCE is HistoryDatabase) return INSTANCE!!
            INSTANCE = Room.databaseBuilder(
                MyApplication.appContext, HistoryDatabase::class.java, "history_database"
            ).build()
            return INSTANCE!!
        }
    }
}