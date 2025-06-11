package com.week1.healing_yuk

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.week1.healing_yuk.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
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

        binding.txtNamaProfile.text = sp?.getString("name", "nama null")
        binding.txtEmailProfile.text = sp?.getString("email", "email null")
        binding.txtJoinProfile.text = sp?.getString("created_at", "tanggal tidak tersedia")
        binding.txtFavouriteProfile.text = sp?.getInt("total_favorites", 0).toString()
    }
}