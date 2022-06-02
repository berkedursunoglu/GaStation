package com.berkedursunoglu.gastation.services

import android.telecom.Call
import com.berkedursunoglu.gastation.models.Fuel
import com.berkedursunoglu.gastation.models.FuelResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Url

interface IAPI {



    @Headers("//")
    @GET
    fun getData(@Url url:String) : Single<FuelResponse>
}