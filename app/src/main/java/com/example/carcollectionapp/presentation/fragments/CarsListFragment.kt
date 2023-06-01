package com.example.carcollectionapp.presentation.fragments

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.fragment.findNavController
import com.example.carcollectionapp.databinding.FragmentCarsListBinding
import com.example.carcollectionapp.presentation.dialogs.SubscriptionDialog
import com.example.carcollectionapp.presentation.recycler.CarListAdapter
import com.example.carcollectionapp.presentation.viewmodel.CarsListViewModel
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class CarsListFragment: BaseFragment<FragmentCarsListBinding, CarsListViewModel>() {

    private lateinit var carListAdapter: CarListAdapter

    private var addCount = -1
    private var viewCount = -1
    private var subTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

    override fun getViewBinding(): FragmentCarsListBinding {
        return FragmentCarsListBinding.inflate(layoutInflater)
    }

    override fun getViewModelClass(): Class<CarsListViewModel> {
        return CarsListViewModel::class.java
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
        viewModel.addCount.observe(this){ count ->
            addCount = count
        }
        viewModel.viewCount.observe(this){ count ->
            viewCount = count
        }
        viewModel.settings.observe(this){ subscriptionTime ->
            subTime = subscriptionTime.toLong()
        }


    }

    override fun setupView() {
        setupRecyclerView()
        setupAddButtonClickListener()
        setupSettingsClickListener()
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
            Toast.makeText(requireActivity(), viewCount.toString(), Toast.LENGTH_SHORT).show()
            if(viewCount <= 0 && subscriptionDisabled()){
                showSubscriptionScreen()
            }
            else{
                viewModel.showCarDetailInfo(car.id, findNavController())
            }
        }
    }

    private fun setupAddButtonClickListener(){
        binding.fabAddCar.setOnClickListener{
            Toast.makeText(requireActivity(), addCount.toString(), Toast.LENGTH_SHORT).show()
            if(addCount <= 0 && subscriptionDisabled()){
                showSubscriptionScreen()
            }
            else{
                viewModel.moveToAddNewCarScreen(findNavController())
            }
        }
    }

    private fun setupSettingsClickListener(){
        binding.settings.setOnClickListener {
            viewModel.moveToSettingsScreen(findNavController())
        }
    }

    private fun showSubscriptionScreen(){
        val subscriptionFragment = SubscriptionDialog()
        subscriptionFragment.onApplyFunc = {
            viewModel.setSubscription()
        }
        subscriptionFragment.show(childFragmentManager, "dialog")
    }

    private fun subscriptionDisabled(): Boolean{
        val time = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        return  time > subTime
    }
}