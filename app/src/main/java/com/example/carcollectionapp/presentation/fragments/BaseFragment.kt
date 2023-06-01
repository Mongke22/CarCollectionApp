package com.example.carcollectionapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.carcollectionapp.CarApp
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding, VM: ViewModel> : Fragment() {

    private var _binding: VB? = null
    val binding
        get() = _binding ?: throw RuntimeException("FragmentBinding is null")
    protected abstract fun getViewBinding(): VB

    protected lateinit var viewModel: VM
    protected abstract fun getViewModelClass(): Class<VM>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract fun injectDependencies()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        injectDependencies()
        _binding = getViewBinding()
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[getViewModelClass()]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        setupView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected open fun observeData(){}

    protected open fun setupView(){}

}