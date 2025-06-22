package com.week1.healing_yuk

import android.R
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.week1.healing_yuk.databinding.ActivityNewHealingPlaceBinding
import java.util.Arrays

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View

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

        //TAMPILKAN IMAGE PREVIEW KETIKA USER MEMASUKKAN URL
        val handler = Handler(Looper.getMainLooper())
        var imageUrlRunnable: Runnable? = null

        binding.editNewImageURL.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Hapus callback sebelumnya jika ada
                imageUrlRunnable?.let { handler.removeCallbacks(it) }
            }

            override fun afterTextChanged(s: Editable?) {
                imageUrlRunnable = Runnable {
                    val url = s.toString()
                    if (url.isNotBlank()) {
                        val builder = Picasso.Builder(this@NewHealingPlace)
                        builder.listener { _, _, exception ->
                            exception.printStackTrace()
                        }

                        builder.build().load(url)
                            .into(binding.imagePreview, object : com.squareup.picasso.Callback {

                                //Jika success
                                override fun onSuccess() {
                                    binding.imagePreview.visibility = View.VISIBLE
                                }

                                //Jika gagal
                                override fun onError(e: Exception?) {
                                    binding.imagePreview.visibility = View.GONE
                                    Toast.makeText(this@NewHealingPlace, "Failed to load image", Toast.LENGTH_SHORT).show()
                                }
                            })

                    //Jika URL kosong
                    } else {
                        binding.imagePreview.visibility = View.GONE
                    }
                }
                //Jalankan setelah user berhenti mengetik 1 detik
                handler.postDelayed(imageUrlRunnable!!, 1000)
            }
        })

        binding.btnNewSubmit.setOnClickListener {
            val name = binding.editNewLocationName.text.toString().trim()
            val imageUrl = binding.editNewImageURL.text.toString().trim()
            val category = binding.spinnerNewCategory.selectedItem.toString().trim()
            val shortDesc = binding.editNewShortDesc.text.toString().trim()
            val fullDesc = binding.editNewLongDesc.text.toString().trim()

            // Validasi inputan
            if (name.isEmpty() || imageUrl.isEmpty() || category.isEmpty() || shortDesc.isEmpty() || fullDesc.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val q = Volley.newRequestQueue(this)
            val url = "https://ubaya.xyz/hybrid/160422057/new_healing_places.php"

            val stringRequest = object: StringRequest( // pakai anonymous class (class yg nggak pake file baru)
                Request.Method.POST,
                url,
                {Toast.makeText(this, "Location has been added", Toast.LENGTH_SHORT).show()}, // jika sukses
                {Log.e("apiresult", it.message.toString())} // jika gagal
            ) // header
            {
                override fun getParams(): Map<String, String> {
                    return hashMapOf(
                        "name" to name,
                        "image_url" to imageUrl,
                        "category" to category,
                        "short_desc" to shortDesc,
                        "full_desc" to fullDesc,
                    )
                }
            } // body
            q.add(stringRequest)
        }
    }
}