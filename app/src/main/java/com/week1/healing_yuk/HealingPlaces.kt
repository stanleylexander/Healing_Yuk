package com.week1.healing_yuk

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HealingPlaces(
    val id:Int,
    var name:String,
    var image_url:String,
    var category:String,
    var short_desc:String,
    var full_desc: String
) : Parcelable {
    override fun toString() = name
}