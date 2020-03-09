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
        requestWork()
    }

    private fun initBinding(binding: ActivityMainBinding) {
        val adapter = HistoryAdapter()
        binding.list.adapter = adapter

        val dataSourceFactory = HistoryDatabase.getInstance().historyDao().getAll()
        val livePagedList = LivePagedListBuilder(dataSourceFactory, 10).build()

        livePagedList.observe(this, Observer { adapter.submitList(it) })
    }

    private fun requestWork() {
        val wm = WorkManager.getInstance(this)
        val work =
            PeriodicWorkRequest.Builder(LoggingWorker::class.java, 15, TimeUnit.MINUTES)
                .apply {
                    val data = Data.Builder()
                        .putString("created_at", Date().toString())
                        .build()
                    setInputData(data)

                    val randomSec = (Math.random() * 60).toLong()
                    Log.d("initial_delay", randomSec.toString())
                    setInitialDelay(randomSec, TimeUnit.SECONDS)

                    // アイドル中だけ実施させたいとき
//                    val constraints = Constraints.Builder()
//                        .setRequiresDeviceIdle(true)
//                        .build()
//                    setConstraints(constraints)
                }.build()
        wm.enqueueUniquePeriodicWork("EVERY_15MIN_HIST", ExistingPeriodicWorkPolicy.KEEP, work)
    }
}
