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
import com.week1.healing_yuk.databinding.ActivityChangePasswordBinding
import org.json.JSONObject

class ChangePassword : AppCompatActivity() {
    private lateinit var binding:ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnChangePassword.setOnClickListener {
            val sp: SharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE)
            val id = sp.getInt("id", -1)

            if (id == -1) {
                Toast.makeText(this, "User ID tidak ditemukan. Silakan login ulang.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val oldPassword = binding.editOldPassword.text.toString()
            val newPassword = binding.editNewPassword.text.toString()
            val repeatPassword = binding.editRepeatPassword.text.toString()

            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Mohon isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (repeatPassword != newPassword) {
                Toast.makeText(this, "Password baru dan password pengulangan tidak sama", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val q = Volley.newRequestQueue(this)
            val url = "https://ubaya.xyz/native/160422029/change_password.php"

            val stringRequest = object : StringRequest(
                Request.Method.POST,
                url,
                { response ->
                    Log.d("apiresult", response)
                    val obj = JSONObject(response)

                    if (obj.getString("result") == "success update password") {
                        Toast.makeText(this, "Password berhasil diubah", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val msg = obj.optString("message", "Terjadi kesalahan saat mengubah password")
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                    }
                },
                { error ->
                    Log.e("apiresult", error.message.toString())
                    Toast.makeText(this, "Gagal mengubah password: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            ) {
                override fun getParams(): Map<String, String> {
                    return hashMapOf(
                        "id" to id.toString(),
                        "old_password" to oldPassword,
                        "new_password" to newPassword
                    )
                }
            }

            q.add(stringRequest)
        }

    }
}