package com.week1.healing_yuk

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import com.week1.healing_yuk.databinding.ActivityHealingPlaceDetailBinding

class HealingPlaceDetail : AppCompatActivity() {
    private lateinit var binding: ActivityHealingPlaceDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealingPlaceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<HealingPlaces>("healing_place")

        if (data != null) {
            binding.txtDetailName.text = data.name
            binding.txtDetailCategory.text = data.category
            binding.txtDetailDescription.text = data.full_desc
            Picasso.get().load(data.image_url).into(binding.imgDetail)
        }
    }
}