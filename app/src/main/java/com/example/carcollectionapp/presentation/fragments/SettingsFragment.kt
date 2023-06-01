package com.example.carcollectionapp.presentation.fragments

import androidx.navigation.fragment.findNavController
import com.example.carcollectionapp.CarApp
import com.example.carcollectionapp.databinding.FragmentSettingsBinding
import com.example.carcollectionapp.presentation.viewmodel.SettingsViewModel

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override fun getViewBinding(): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun getViewModelClass(): Class<SettingsViewModel> {
        return SettingsViewModel::class.java
    }

    override fun setupView() {
        binding.btnReset.setOnClickListener {
            viewModel.resetSettings(findNavController())
        }
    }
    private val component by lazy {
        (requireActivity().application as CarApp).component
    }

    override fun injectDependencies() {
        component.inject(this)
    }

}