package com.week1.healing_yuk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.week1.healing_yuk.databinding.FragmentProfileBinding
import org.json.JSONObject

class ProfileFragment : Fragment() {
    private var LokasiHealingsFavourite: ArrayList<HealingPlaces> = ArrayList()
    private lateinit var binding:FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sp = this.getActivity()?.getSharedPreferences("user", Context.MODE_PRIVATE)
        fetchData()

        binding.editNameProfile.setText(sp?.getString("name", "nama null"))
        binding.editEmailProfile.setText(sp?.getString("email", "email null"))
        binding.editDateProfile.setText(sp?.getString("created_at", "tanggal tidak tersedia"))
        binding.editFavouriteLoc.setText("0")
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData() {
        val sp = this.getActivity()?.getSharedPreferences("user", Context.MODE_PRIVATE)
        val id = sp?.getInt("id",0)

        val q = Volley.newRequestQueue(requireContext())
        val url = "https://ubaya.xyz/hybrid/160422057/get_user_favourite.php?id="+id.toString()

        val stringRequest = StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<HealingPlaces>>() {}.type
                    LokasiHealingsFavourite.clear()
                    LokasiHealingsFavourite.addAll(Gson().fromJson(data.toString(), sType))
                    binding.editFavouriteLoc.setText(LokasiHealingsFavourite.size.toString())
                    Log.d("apiresult", LokasiHealingsFavourite.toString())
                }
            },
            { Log.e("apiresult", it.message.toString()) }
        )
        q.add(stringRequest)
    }
}