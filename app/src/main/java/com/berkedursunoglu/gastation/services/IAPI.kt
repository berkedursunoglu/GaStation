package com.berkedursunoglu.gastation.services

import android.telecom.Call
import com.berkedursunoglu.gastation.models.Fuel
import com.berkedursunoglu.gastation.models.FuelResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface IAPI {



    @Headers("authorization: apikey 52NQpPwrHlnAZlI0Rnx6Sm:69ADQmuknHYOWxkgV1FVGd","content-type: application/json")
    @GET ("gasPrice/turkeyGasoline?district=kadikoy&city=istanbul")
    fun getData(): Single<FuelResponse>
}