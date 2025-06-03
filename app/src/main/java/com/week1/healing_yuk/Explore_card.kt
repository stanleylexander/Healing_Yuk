package com.week1.healing_yuk

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.week1.healing_yuk.databinding.FragmentExploreCardBinding

class Explore_card(var LokasiHealings:ArrayList<HealingPlaces>) : RecyclerView.Adapter<Explore_card.ExploreViewHolder>() {
    class ExploreViewHolder(val binding: FragmentExploreCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        val binding = FragmentExploreCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExploreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return LokasiHealings.size
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        var url = LokasiHealings[position].image_url
        val builder = Picasso.Builder(holder.itemView.context)
        builder.listener {picasso, uri, exception -> exception.printStackTrace()}

        with(holder.binding){
            Picasso.get().load(url).into(imageView)
            txtTitle.text = LokasiHealings[position].name
            txtCategory.text = LokasiHealings[position].category
            txtSubtitle.text = LokasiHealings[position].short_desc

            btnReadMore.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, HealingPlaceDetail::class.java)
                intent.putExtra("healing_place", LokasiHealings[position])
                context.startActivity(intent)
            }
        }
    }
}