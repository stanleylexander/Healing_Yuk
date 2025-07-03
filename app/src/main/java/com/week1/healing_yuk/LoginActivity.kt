package com.week1.healing_yuk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.week1.healing_yuk.databinding.ActivityLoginBinding
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.buttonLogin.setOnClickListener {
            val q = Volley.newRequestQueue(this)
            //val url = "https://ubaya.xyz/hybrid/160422057/login_healing.php"
            val url = "https://ubaya.xyz/native/160422025/login_healing.php"
            val stringRequest = object: StringRequest( // pakai anonymous class (class yg nggak pake file baru)
                Request.Method.POST,
                url,
                {
                    Log.d("apiresult",it)
                    val obj = JSONObject(it)
                    if (obj.getString("result") == "OK") {
                        val sp:SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
                        val editor = sp.edit()
                        editor.putInt("id", obj.getInt("id"))
                        editor.putString("name", obj.getString("name"))
                        editor.putString("email", obj.getString("email"))
                        editor.putString("created_at", obj.getString("created_at"))
                        editor.putInt("total_favorites", obj.getInt("total_favorites"))
                        editor.apply()
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Login failed: ${obj.getString("message")}", Toast.LENGTH_SHORT).show()
                    }
                }, // jika sukses
                {
                    Log.e("apiresult", it.message.toString())
                    Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()
                }
            ) // header
            {
                override fun getParams(): Map<String, String> {
                    return hashMapOf(
                        "email" to binding.editTextEmail.text.toString(),
                        "password" to binding.editTextPassword.text.toString()
                    )
                }
            } // body
            q.add(stringRequest)
        }
    }
}