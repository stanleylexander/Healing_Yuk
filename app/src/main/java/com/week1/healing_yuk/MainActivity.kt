package com.week1.healing_yuk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.week1.healing_yuk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val fragments:ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sp = getSharedPreferences("user", Context.MODE_PRIVATE)
        if (!sp.contains("id")) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        fragments.add(ExploreFragment())
        fragments.add(FavouriteFragment())
        fragments.add(ProfileFragment())
        binding.viewPager.adapter = MainAdapter(this,fragments)

        binding.viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNav.selectedItemId = binding.bottomNav.menu.getItem(position).itemId
                // Or: binding.bottomNav.selectedItemId = binding.bottomNav.menu[position].itemId

            }
        })
        binding.bottomNav.setOnItemSelectedListener {
            binding.viewPager.currentItem = when(it.itemId) {
                R.id.ItemExplore -> -0
                R.id.ItemFavourite -> 1
                R.id.ItemProfile -> 2
                else -> 0 // default to home
            }
            true
        }
    }
}