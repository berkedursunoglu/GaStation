package com.berkedursunoglu.gastation.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Fuel(
    @SerializedName("katkili")
    @Expose
    val fuelplus: String,

    @SerializedName("benzin")
    @Expose
    val fuel: String,

    @SerializedName("marka")
    @Expose
    val brand: String
)