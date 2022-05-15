package com.berkedursunoglu.gastation.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.berkedursunoglu.gastation.fragments.ChoosenFragmentDirections

class ChoosenFragmentViewModels : ViewModel() {

    private val whatType = MutableLiveData<String>()

    fun navigate(viewChoosen: View, type: String) {
        whatType.value = type
        val action = ChoosenFragmentDirections.choosenToDetail()
        action.type = type
        Navigation.findNavController(viewChoosen).navigate(action)
    }
}