package com.berkedursunoglu.gastation.services

import com.berkedursunoglu.gastation.models.Fuel
import com.berkedursunoglu.gastation.models.FuelResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitServicesAPI {


    private val BASE_URL = "https://api.collectapi.com/"

    private val api =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(IAPI::class.java)

    fun getData(url:String): Single<FuelResponse> {
        return api.getData(url)
    }
}