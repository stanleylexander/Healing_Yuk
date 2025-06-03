package com.week1.healing_yuk

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
                val q = Volley.newRequestQueue(this)
                val url = "http://192.168.100.11/nmp_project/set_favourite.php"

                val stringRequest = object: StringRequest( // pakai anonymous class (class yg nggak pake file baru)
                    Request.Method.POST,
                    url,
                    {   // success
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    },{ // failed
                        Log.e("apiresult", it.message.toString())
                    }
                ) // header
                {
                    override fun getParams(): Map<String, String> {
                        return hashMapOf(
                            "id_user" to " id user ",
                            "id_healing_place" to data.id.toString()
                        )
                    }
                } // body
                q.add(stringRequest)
            }
        }
    }
}