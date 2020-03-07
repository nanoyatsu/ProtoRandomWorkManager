package com.nanoyatsu.proto.randomWorkManager.data.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nanoyatsu.proto.randomWorkManager.data.database.entity.History

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: History)

    @Query("select * from history order by id")
    fun getAll(): DataSource.Factory<Int, History>
}