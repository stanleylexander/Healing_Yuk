package com.week1.healing_yuk

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainAdapter (val activity: AppCompatActivity, val fragments:ArrayList<Fragment>)
    : FragmentStateAdapter(activity) {


    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}