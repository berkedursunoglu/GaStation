package com.berkedursunoglu.gastation.viewmodels

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


    fun fuelcost(view: View, fragment: FragmentDetailBinding, fueltype: String) {
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
                choosedCity.value = p0?.selectedItem.toString()
                retrofitData(fragment)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    fun retrofitData(fragment: FragmentDetailBinding) {
        disposeOn.add(
            retrofitServicesAPI.getData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<FuelResponse>() {
                    override fun onSuccess(t: FuelResponse) {
                        fuelArray.value = t.fuel
                        fragment.progressBar.visibility = View.GONE
                        fragment.fuelCostTextView.text = t.fuel[1].fuel
                        fragment.fuelCostTextView.visibility = View.VISIBLE
                    }
                    override fun onError(e: Throwable) {
                        println(e.message)
                    }
                })
        )
    }

    fun tankFuelCost(fragment: FragmentDetailBinding){
        if(fuelArray.value != null){
            var doubleFuel = fuelArray.value!![1].fuel.toFloatOrNull()
            val check = fragment.editTextTextPersonName.text
            if (check.equals(String())){
                fragment.editTextTextPersonName.hint = "Sayı Girilmeli"
            }else{
                var tankLT = fragment.editTextTextPersonName.text.toString()
                fragment.tankTextView.text = "Depo dolumu: ${(doubleFuel?.times(tankLT.toFloat())).toString()} TL"
            }
        }
    }

    fun kmCoast(fragment: FragmentDetailBinding){
        if(fuelArray.value != null){
            var doubleFuel = fuelArray.value!![1].fuel.toFloatOrNull()
            val check = fragment.editTextTextPersonName2.text
            if (check.equals(String())){
                fragment.editTextTextPersonName2.hint = "Sayı Girilmeli"
            }else{
                var aKm = fragment.editTextTextPersonName2.text.toString()
                var aKMLT = aKm.toFloatOrNull()?.div(100)
                var aKmFuelCoast = aKMLT?.times(doubleFuel!!)
                fragment.kmTextView.text = "Km'de ${aKmFuelCoast} TL"
            }

        }
    }

    override fun onCleared() {
        super.onCleared()
        disposeOn.clear()
    }

}

