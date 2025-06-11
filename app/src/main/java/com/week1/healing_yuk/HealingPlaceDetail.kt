package com.week1.healing_yuk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.week1.healing_yuk.databinding.ActivityHealingPlaceDetailBinding
import org.json.JSONObject

class HealingPlaceDetail : AppCompatActivity() {
    private lateinit var binding: ActivityHealingPlaceDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealingPlaceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<HealingPlaces>("healing_place")

        if (data != null) {
            with(binding){
                txtDetailName.text = data.name
                txtDetailCategory.text = data.category
                txtDetailDescription.text = data.full_desc
                Picasso.get().load(data.image_url).into(imgDetail)
            }

            binding.btnFavourite.setOnClickListener {
                val sp = getSharedPreferences("user", Context.MODE_PRIVATE)
                val user_id = sp?.getInt("id",0)

                val q = Volley.newRequestQueue(this)
                val url = "https://ubaya.xyz/hybrid/160422057/set_favourite.php"

                val stringRequest = object: StringRequest( // pakai anonymous class (class yg nggak pake file baru)
                    Request.Method.POST,
                    url,
                    {   // success
                        Log.d("apiresult",it)
                        val obj = JSONObject(it)
                        if (obj.getString("result") == "OK") {
                            val successMessage = obj.getString("message")
                            Toast.makeText(this, successMessage, Toast.LENGTH_SHORT).show()
                        } else {
                            val errorMessage = obj.getString("message")
                            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show() // Tampilkan pesan error spesifik
                        }
                    },{ // failed
                        Log.e("apiresult", it.message.toString())
                        Toast.makeText(this, "somehow error bruh", Toast.LENGTH_SHORT).show()
                    }
                ) // header
                {
                    override fun getParams(): Map<String, String> {
                        return hashMapOf(
                            "place_id" to data.id.toString(),
                            "user_id" to user_id.toString()
                        )
                    }
                } // body
                q.add(stringRequest)
            }
        }
    }
}