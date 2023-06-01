package com.example.carcollectionapp.presentation.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.carcollectionapp.CarApp
import com.example.carcollectionapp.R
import com.example.carcollectionapp.databinding.FragmentDetailInfoBinding
import com.example.carcollectionapp.domain.CarInfo
import com.example.carcollectionapp.presentation.viewmodel.DetailInfoViewModel


class DetailInfoFragment : BaseFragment<FragmentDetailInfoBinding, DetailInfoViewModel>() {

    private val args by navArgs<DetailInfoFragmentArgs>()

    override fun getViewBinding(): FragmentDetailInfoBinding {
        return FragmentDetailInfoBinding.inflate(layoutInflater)
    }

    override fun getViewModelClass(): Class<DetailInfoViewModel> {
        return DetailInfoViewModel::class.java
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getCar(args.carId)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun observeData() {
        viewModel.theCar.observe(this) { car ->
            setInfo(car)
        }
    }

    private fun setInfo(car: CarInfo) {
        binding.carAddDate.text = car.insertionDate
        binding.carPower.text =
            String.format(resources.getString(R.string.car_power_template), car.engineCapacity)

        if (car.carName.isEmpty()) {
            binding.carName.text = resources.getString(R.string.unknown_string)
        } else {
            binding.carName.text = car.carName
        }

        if (car.picturePath.isEmpty()) {
            binding.ivCarPicture.setImageResource(R.drawable.image_placeholder)
        } else {
            binding.ivCarPicture.setImageURI(Uri.parse(car.picturePath))
        }

        if (car.productionDate.isEmpty()) {
            binding.carProductionDate.text =
                String.format(resources.getString(R.string.production_date_template),
                    resources.getString(R.string.unknown_string))
        } else {
            binding.carProductionDate.text =
                String.format(resources.getString(R.string.production_date_template),
                    car.productionDate)
        }


    }
    private val component by lazy {
        (requireActivity().application as CarApp).component
    }

    override fun injectDependencies() {
        component.inject(this)
    }

}