package com.week1.healing_yuk

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.week1.healing_yuk.Explore_card.ExploreViewHolder
import com.week1.healing_yuk.databinding.FragmentExploreCardBinding
import com.week1.healing_yuk.databinding.FragmentFavouriteCardBinding

class Favourite_card(
    var LokasiHealingsFavourite:ArrayList<HealingPlaces>,
    val onItemClick: (HealingPlaces) -> Unit) : RecyclerView.Adapter<Favourite_card.FavouriteViewHolder>() {
    class FavouriteViewHolder(val binding: FragmentFavouriteCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val binding = FragmentFavouriteCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteViewHolder(binding)
    }

    override fun getItemCount(): Int = LokasiHealingsFavourite.size

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        var url = LokasiHealingsFavourite[position].image_url
        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener {picasso, uri, exception -> exception.printStackTrace()}

        with(holder.binding){
            Picasso.get().load(url).into(imgFavourite)
            if(LokasiHealingsFavourite[position].name.length <= 30){txtFavouriteName.text = LokasiHealingsFavourite[position].name}
            else{txtFavouriteName.text = LokasiHealingsFavourite[position].name.slice(0..30)+"..."}
            txtFavouriteCategory.text = LokasiHealingsFavourite[position].category

            root.setOnClickListener { onItemClick(LokasiHealingsFavourite[position]) }
        }
    }
}