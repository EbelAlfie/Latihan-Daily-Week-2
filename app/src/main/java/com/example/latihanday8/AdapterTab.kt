package com.example.latihanday8

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AdapterTab(activity: AppCompatActivity, val totalFragment: Int): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return totalFragment
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) LoginPageFragment() else ListAlamatFragment()
    }
}