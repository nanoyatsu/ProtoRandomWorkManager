package com.nanoyatsu.proto.randomWorkManager.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.nanoyatsu.proto.randomWorkManager.R
import com.nanoyatsu.proto.randomWorkManager.backGround.LoggingWorker
import com.nanoyatsu.proto.randomWorkManager.data.database.HistoryDatabase
import com.nanoyatsu.proto.randomWorkManager.databinding.ActivityMainBinding
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initBinding(binding)
    }

    private fun initBinding(binding: ActivityMainBinding) {
        val adapter = HistoryAdapter()
        binding.list.adapter = adapter

        val wm = WorkManager.getInstance(this)
        val work =
            PeriodicWorkRequest.Builder(LoggingWorker::class.java, 15, TimeUnit.MINUTES)
                .apply {
                    val data = Data.Builder()
                        .apply { putString("created_at", Date().toString()) }.build()
                    setInputData(data)
                    val random = (Math.random() * 60 * 60).toLong()
//                    val random = (Math.random() * 60 * 60 * 24).toLong()
                    Log.d("initial_delay", random.toString())
                    setInitialDelay(random, TimeUnit.SECONDS)
                }.build()
        wm.enqueueUniquePeriodicWork("EVERY_HOUR_HIST", ExistingPeriodicWorkPolicy.KEEP, work)

        val dataSourceFactory = HistoryDatabase.getInstance().historyDao().getAll()
        val livePagedList = LivePagedListBuilder(dataSourceFactory, 10).build()

        livePagedList.observe(this, Observer { adapter.submitList(it) })
    }
}
