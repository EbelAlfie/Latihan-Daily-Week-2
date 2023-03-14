package com.example.latihanday8

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.latihanday8.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity(): AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val TABS = arrayOf(
        "Login Page",
        "List Alamat"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTab()
    }

    private fun setTab() {
        setFragment()
    }

    private fun setFragment() {
        val tabAdapter = AdapterTab(this, TABS.size)
        binding.viewPager.adapter = tabAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.text = TABS[pos]
        }.attach()
    }
}