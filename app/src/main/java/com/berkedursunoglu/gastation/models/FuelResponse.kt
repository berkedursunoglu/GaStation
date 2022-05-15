package com.berkedursunoglu.gastation.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FuelResponse(
    @SerializedName("success")
    @Expose
    var success: Boolean,
    @SerializedName("result")
    @Expose
    var fuel: List<Fuel>
) {
}