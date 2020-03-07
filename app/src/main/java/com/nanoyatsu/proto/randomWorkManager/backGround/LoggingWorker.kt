package com.nanoyatsu.proto.randomWorkManager.backGround

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.nanoyatsu.proto.randomWorkManager.data.database.HistoryDatabase
import com.nanoyatsu.proto.randomWorkManager.data.database.entity.History
import java.util.*

class LoggingWorker(ctx: Context, private val params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        val dao = HistoryDatabase.getInstance().historyDao()
        val now = Date().toString()
        val history = History(0, now, inputData.getString("created_at") ?: "null")
        return try {
            dao.insert(history)
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}