package com.berkedursunoglu.gastation.viewmodels

import android.annotation.SuppressLint
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berkedursunoglu.gastation.R
import com.berkedursunoglu.gastation.databinding.FragmentDetailBinding
import com.berkedursunoglu.gastation.models.Fuel
import com.berkedursunoglu.gastation.models.FuelResponse
import com.berkedursunoglu.gastation.services.RetrofitServicesAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DetailFragmentViewModels : ViewModel() {

    val choosedCity: MutableLiveData<String> = MutableLiveData()
    val fuelArray: MutableLiveData<List<Fuel>> = MutableLiveData()
    private val retrofitServicesAPI = RetrofitServicesAPI()
    private val disposeOn: CompositeDisposable = CompositeDisposable()
    val type: MutableLiveData<String> = MutableLiveData()


    fun fuelCost(view: View, fragment: FragmentDetailBinding, fueltype: String) {
        val arrayAdapter = ArrayAdapter.createFromResource(
            view.context,
            R.array.city_array,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        fragment.citySpinner.adapter = arrayAdapter
        fragment.citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                fragment.fuelCostTextView.visibility = View.GONE
                fragment.progressBar.visibility = View.VISIBLE
                var city = p0?.selectedItem.toString().lowercase()
                choosedCity.value = city
                var fuelType = type.value
                println("type ${fuelType} şehir ${city}")
                var url = "gasPrice${fuelType}?city=${city}"
                retrofitData(fragment,url)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    fun retrofitData(fragment: FragmentDetailBinding,url:String) {
        disposeOn.add(
            retrofitServicesAPI.getData(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<FuelResponse>() {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(t: FuelResponse) {
                        fuelArray.value = t.fuel
                        fragment.progressBar.visibility = View.GONE
                        fragment.fuelCostTextView.text = "${t.fuel[1].fuel} TL"
                        fragment.fuelCostTextView.visibility = View.VISIBLE
                    }
                    override fun onError(e: Throwable) {
                        println("retrofit error!")
                        println(e.message)
                    }
                })
        )
    }

    @SuppressLint("SetTextI18n")
    fun tankFuelCost(fragment: FragmentDetailBinding) {
        if (fuelArray.value == null) {
        } else {
            val tank = fragment.editTextTextPersonName.text.toString()
            if (tank.toFloatOrNull() == null) {
                fragment.tankTextView.text = "Sayı Girmelisiniz.."
            } else {
                val tankCoast =
                    tank.toFloatOrNull()!! * fuelArray.value?.get(1)?.fuel?.toFloatOrNull()!!
                fragment.tankTextView.text = "Aracınız ${tankCoast.toString()} TL'ye doluyor."
            }
        }
    }

    fun kmCoast(fragment: FragmentDetailBinding) {
        if (fuelArray.value == null) {
        } else {
            val km100 = fragment.editTextTextPersonName2.text.toString()
            if (km100.toFloatOrNull() == null) {
                fragment.kmTextView.text = "Sayı Girmelisiniz.."
            } else {
                val aKmLt = km100.toFloatOrNull()!! / 100
                val kmFuelCost = aKmLt * fuelArray.value!![1].fuel.toFloatOrNull()!!
                fragment.kmTextView.text = "Aracınız Km'de ${kmFuelCost} TL yakıyor."
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposeOn.clear()
    }

}

