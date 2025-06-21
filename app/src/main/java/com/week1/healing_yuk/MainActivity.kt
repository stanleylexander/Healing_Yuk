package com.week1.healing_yuk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.week1.healing_yuk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val fragments:ArrayList<Fragment> = ArrayList()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //DRAWER HAMBURGER ICON
        var drawerToggle = ActionBarDrawerToggle(this, binding.main,
            binding.toolbar, R.string.app_name, R.string.app_name)
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()
        drawerToggle.drawerArrowDrawable.color = ContextCompat.getColor(this, android.R.color.black)

        binding.navigationView.setNavigationItemSelectedListener{
            when(it.itemId) {
                R.id.itemChangePassword -> Snackbar.make(this,binding.root, "Change Password", Snackbar.LENGTH_SHORT).show()
                R.id.itemLogout -> Snackbar.make(this,binding.root, "Logout", Snackbar.LENGTH_SHORT).show()
            }
            binding.main.closeDrawer(GravityCompat.START)
            true
        }



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