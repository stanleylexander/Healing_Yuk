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
import com.week1.healing_yuk.databinding.ActivitySignUpBinding
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSubmit.setOnClickListener {
            val q = Volley.newRequestQueue(this)
            val url = "https://ubaya.xyz/hybrid/160422057/register_healing.php"

            if(binding.editTextPasswordReg.text.toString() == binding.editTextConfirmPasswordReg.text.toString()){
                val stringRequest = object: StringRequest( // pakai anonymous class (class yg nggak pake file baru)
                    Request.Method.POST,
                    url,
                    {
                        Log.d("apiresult",it)
                        val obj = JSONObject(it)
                        if (obj.getString("result") == "OK") {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            val errorMessage = obj.getString("message")
                            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show() // Tampilkan pesan error spesifik
                        }
                    }, // jika sukses
                    {
                        Log.e("apiresult", it.message.toString())
                        Toast.makeText(this, "register gagal", Toast.LENGTH_SHORT).show()
                    }
                ) // header
                {
                    override fun getParams(): Map<String, String> {
                        return hashMapOf(
                            "name" to binding.editTextNameReg.text.toString(),
                            "email" to binding.editTextEmailReg.text.toString(),
                            "password" to binding.editTextPasswordReg.text.toString()
                        )
                    }
                } // body
                q.add(stringRequest)
            }
            else{Toast.makeText(this, "confirm password not match", Toast.LENGTH_SHORT).show()}
        }
    }
}