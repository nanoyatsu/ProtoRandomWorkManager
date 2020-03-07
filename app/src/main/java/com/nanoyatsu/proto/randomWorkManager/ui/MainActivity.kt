package com.nanoyatsu.proto.randomWorkManager.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import com.nanoyatsu.proto.randomWorkManager.R
import com.nanoyatsu.proto.randomWorkManager.data.database.HistoryDatabase
import com.nanoyatsu.proto.randomWorkManager.databinding.ActivityMainBinding

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

        val dataSourceFactory = HistoryDatabase.getInstance().historyDao().getAll()
        val livePagedList = LivePagedListBuilder(dataSourceFactory, 10).build()

        livePagedList.observe(this, Observer { adapter.submitList(it) })
    }
}
