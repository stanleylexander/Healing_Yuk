package com.week1.healing_yuk

import android.content.Intent
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
import com.week1.healing_yuk.databinding.FragmentExploreBinding
import org.json.JSONObject

class ExploreFragment : Fragment() {
    private lateinit var binding: FragmentExploreBinding
    private var LokasiHealings: ArrayList<HealingPlaces> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ExploreRecView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = Explore_card(LokasiHealings)
        }

        fetchData()

        binding.fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), NewHealingPlace::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    private fun fetchData() {
        val q = Volley.newRequestQueue(requireContext())
        val url = "http://192.168.100.11/nmp_project/get_healing_places.php"

        val stringRequest = StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<HealingPlaces>>() {}.type
                    LokasiHealings.clear()
                    LokasiHealings.addAll(Gson().fromJson(data.toString(), sType))
                    binding.ExploreRecView.adapter?.notifyDataSetChanged()
                    Log.d("apiresult", LokasiHealings.toString())
                }
            },
            { Log.e("apiresult", it.message.toString()) }
        )
        q.add(stringRequest)
    }
}
