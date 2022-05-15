package com.berkedursunoglu.gastation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.berkedursunoglu.gastation.R
import com.berkedursunoglu.gastation.databinding.FragmentDetailBinding
import com.berkedursunoglu.gastation.viewmodels.DetailFragmentViewModels


class DetailFragment : Fragment() {

    private lateinit var dataBinding: FragmentDetailBinding
    private lateinit var viewModel: DetailFragmentViewModels

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        viewModel = ViewModelProviders.of(this).get(DetailFragmentViewModels::class.java)
        dataBinding.fuelCostTextView.visibility = View.GONE
        val args: DetailFragmentArgs by navArgs()
        viewModel.fuelCost(dataBinding.root,dataBinding,args.type)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.tankButton.setOnClickListener {
            viewModel.tankFuelCost(dataBinding)
        }

        dataBinding.kmButton.setOnClickListener {
            viewModel.kmCoast(dataBinding)
        }
    }
}