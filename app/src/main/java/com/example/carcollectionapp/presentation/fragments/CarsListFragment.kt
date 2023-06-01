package com.example.carcollectionapp.presentation.fragments

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.carcollectionapp.CarApp
import com.example.carcollectionapp.databinding.FragmentCarsListBinding
import com.example.carcollectionapp.domain.CarInfo
import com.example.carcollectionapp.presentation.dialogs.FilterDialog
import com.example.carcollectionapp.presentation.dialogs.SubscriptionDialog
import com.example.carcollectionapp.presentation.recycler.CarListAdapter
import com.example.carcollectionapp.presentation.viewmodel.CarsListViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class CarsListFragment: BaseFragment<FragmentCarsListBinding, CarsListViewModel>() {

    companion object{
        const val UNKNOWN_INT = -1
    }

    @Inject
    lateinit var carListAdapter: CarListAdapter

    private var addCount = UNKNOWN_INT
    private var viewCount = UNKNOWN_INT

    private var subTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

    private var filterDirectionDown = true
    private var filterParameterName = false
    private var itemsList: List<CarInfo> = listOf()

    private val component by lazy {
        (requireActivity().application as CarApp).component
    }

    override fun injectDependencies() {
        component.inject(this)
    }
    override fun getViewBinding(): FragmentCarsListBinding {
        return FragmentCarsListBinding.inflate(layoutInflater)
    }

    override fun getViewModelClass(): Class<CarsListViewModel> {
        return CarsListViewModel::class.java
    }

    override fun observeData() {
        setupItemObserver()
        setupSubscriptionObserver()
        setupFilterObserver()
    }

    private fun setupItemObserver(){
        viewModel.carList.observe(this){ cars ->
            if(cars.isEmpty()){
                binding.tvNoItems.visibility = View.VISIBLE
                binding.rvCars.visibility = View.GONE
            }else{
                binding.tvNoItems.visibility = View.GONE
                binding.rvCars.visibility = View.VISIBLE
                itemsList = cars
                carListAdapter.submitList(cars)
            }
        }
    }

    private fun setupSubscriptionObserver(){
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

    private fun setupFilterObserver(){
        viewModel.filterDirectionDown.observe(this){ _filterDirectionDown ->
            filterDirectionDown = _filterDirectionDown
            applyFilter()
        }
        viewModel.filterParameterName.observe(this){ _filterParameterName ->
            filterParameterName = _filterParameterName
            applyFilter()
        }
    }

    override fun setupView() {
        setupRecyclerView()
        setupAddButtonClickListener()
        setupSettingsClickListener()
        setupFilterClickListener()
    }

    private fun setupRecyclerView(){
        val rvCarsList = binding.rvCars
        with(rvCarsList) {
            adapter = carListAdapter
        }
        setupItemClickListener()
        setupOnDataChangeListener()
    }

    private fun setupItemClickListener(){
        carListAdapter.itemOnClickListener = { car ->
            if(viewCount <= 0 && subscriptionDisabled()){
                showSubscriptionScreen()
            }
            else{
                viewModel.showCarDetailInfo(car.id, findNavController())
            }
        }
    }

    private fun setupOnDataChangeListener() {
        carListAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                binding.rvCars.scrollToPosition(0)
            }
        })
    }

    private fun setupAddButtonClickListener(){
        binding.fabAddCar.setOnClickListener{
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

    private fun setupFilterClickListener(){
        binding.filter.setOnClickListener{
            val filterFragment = FilterDialog(filterDirectionDown, filterParameterName)
            filterFragment.onApplyFunc = {
                viewModel.setFilter(filterFragment.filterDirectionDown, filterFragment.filterParameterName)
            }
            filterFragment.show(childFragmentManager, "dialog")
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

    private fun applyFilter(){
        val carListToShow = if(filterDirectionDown){
            if(filterParameterName){
                itemsList.sortedByDescending { car ->
                    car.carName
                }
            }else{
                itemsList.sortedByDescending { car ->
                    car.engineCapacity
                }
            }
        }
        else{
            if(filterParameterName){
                itemsList.sortedBy { car ->
                    car.carName
                }
            }else{
                itemsList.sortedBy { car ->
                    car.engineCapacity
                }
            }
        }

        carListAdapter.submitList(carListToShow)
    }




}