package com.week1.healing_yuk

import android.content.Context
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
import com.week1.healing_yuk.databinding.FragmentFavouriteBinding
import org.json.JSONObject

class FavouriteFragment : Fragment() {
    private lateinit var binding:FragmentFavouriteBinding
    private var LokasiHealingsFavourite: ArrayList<HealingPlaces> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.FavouriteRecView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = Favourite_card(LokasiHealingsFavourite) { healingPlace ->
                val intent = Intent(requireContext(), HealingPlaceDetail::class.java)
                intent.putExtra("healing_place", healingPlace)
                startActivity(intent)
            }

        }
        fetchData()
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
                    binding.FavouriteRecView.adapter?.notifyDataSetChanged()
                    Log.d("apiresult", LokasiHealingsFavourite.toString())
                }
            },
            { Log.e("apiresult", it.message.toString()) }
        )
        q.add(stringRequest)
    }
}