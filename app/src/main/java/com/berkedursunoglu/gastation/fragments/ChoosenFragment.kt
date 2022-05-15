package com.berkedursunoglu.gastation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.berkedursunoglu.gastation.R
import com.berkedursunoglu.gastation.databinding.FragmentChoosenBinding
import com.berkedursunoglu.gastation.viewmodels.ChoosenFragmentViewModels
import kotlinx.coroutines.*

class ChoosenFragment : Fragment() {

    private lateinit var dataBinding:FragmentChoosenBinding
    private lateinit var viewModel: ChoosenFragmentViewModels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_choosen, container, false)
        viewModel = ViewModelProviders.of(this).get(ChoosenFragmentViewModels::class.java)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.gasolineButton.setOnClickListener { viewModel.navigate(it,"gasoline") }

        dataBinding.dieselButton.setOnClickListener { viewModel.navigate(it,"diesel") }

        dataBinding.gasButton.setOnClickListener { viewModel.navigate(it,"gas") }

    }

}