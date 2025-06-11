package com.week1.healing_yuk

import android.R
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.week1.healing_yuk.databinding.ActivityNewHealingPlaceBinding
import java.util.Arrays

class NewHealingPlace : AppCompatActivity() {
    private lateinit var binding: ActivityNewHealingPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewHealingPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categories = listOf("cafe", "resto", "warkop", "hotel", "karaoke", "arcade", "playground", "biliard", "bowling", "bar")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerNewCategory.adapter = adapter

        binding.btnNewSubmit.setOnClickListener {
            val q = Volley.newRequestQueue(this)
            val url = "https://ubaya.xyz/hybrid/160422057/new_healing_places.php"

            val stringRequest = object: StringRequest( // pakai anonymous class (class yg nggak pake file baru)
                Request.Method.POST,
                url,
                {Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()}, // jika sukses
                {Log.e("apiresult", it.message.toString())} // jika gagal
            ) // header
            {
                override fun getParams(): Map<String, String> {
                    return hashMapOf(
                        "name" to binding.txtNewName.text.toString(),
                        "image_url" to binding.txtNewImageURL.text.toString(),
                        "category" to binding.spinnerNewCategory.selectedItem.toString(),
                        "short_desc" to binding.txtNewShortDescription.text.toString(),
                        "full_desc" to binding.txtNewLongDescription.text.toString(),
                    )
                }
            } // body
            q.add(stringRequest)
        }
    }
}