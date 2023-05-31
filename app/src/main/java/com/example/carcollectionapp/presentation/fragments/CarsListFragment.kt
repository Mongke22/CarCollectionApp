package com.example.carcollectionapp.presentation.fragments

import android.view.View
import androidx.activity.OnBackPressedCallback
import com.example.carcollectionapp.databinding.FragmentCarsListBinding
import com.example.carcollectionapp.presentation.recycler.CarListAdapter
import com.example.carcollectionapp.presentation.viewmodel.CarsListViewModel

class CarsListFragment: BaseFragment<FragmentCarsListBinding, CarsListViewModel>() {

    private lateinit var carListAdapter: CarListAdapter

    companion object{
        fun newInstance() = CarsListFragment()
    }

    override fun getViewBinding(): FragmentCarsListBinding {
        return FragmentCarsListBinding.inflate(layoutInflater)
    }

    override fun getViewModelClass(): Class<CarsListViewModel> {
        return CarsListViewModel::class.java
    }

    override fun setUpOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    override fun observeData() {
        viewModel.carList.observe(this){ cars ->
            if(cars.isEmpty()){
                binding.tvNoItems.visibility = View.VISIBLE
                binding.rvCars.visibility = View.GONE
            }else{
                binding.tvNoItems.visibility = View.GONE
                binding.rvCars.visibility = View.VISIBLE
                carListAdapter.submitList(cars)
            }
        }
    }

    override fun setupView() {
        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        val rvCarsList = binding.rvCars
        with(rvCarsList) {
            carListAdapter = CarListAdapter()
            adapter = carListAdapter
        }
        setupItemClickListener()
    }

    private fun setupItemClickListener(){
        carListAdapter.itemOnClickListener = { car ->
            viewModel.showCarDetailInfo(car.id)
        }
    }
}