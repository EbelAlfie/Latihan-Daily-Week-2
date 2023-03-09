package com.example.latihanday8

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity(): AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    private val TABS = arrayOf(
        "Login Page",
        "List Alamat"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setTab()
    }

    private fun setTab() {
        setFragment()
    }

    private fun setFragment() {
        val tabAdapter = AdapterTab(this)
        viewPager.adapter = tabAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, pos ->
            tab.text = TABS[pos]
        }.attach()
    }
    private fun initViews() {
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)
    }
}