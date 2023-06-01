package com.example.carcollectionapp.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.example.carcollectionapp.R
import com.example.carcollectionapp.data.CarRepositoryImpl
import com.example.carcollectionapp.data.SettingsStorage
import com.example.carcollectionapp.domain.CarInfo
import com.example.carcollectionapp.domain.usecase.GetCarInfoListUseCase
import com.example.carcollectionapp.presentation.fragments.CarsListFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class CarsListViewModel @Inject constructor(
    getCarInfoListUseCase: GetCarInfoListUseCase,
    private val storage: SettingsStorage,
) : ViewModel() {

    private var _carList = getCarInfoListUseCase()
    val carList: LiveData<List<CarInfo>>
        get() = _carList

    private var _filterDirectionDown = MutableLiveData<Boolean>()
    val filterDirectionDown: LiveData<Boolean>
        get() = _filterDirectionDown

    private var _filterParameterName = MutableLiveData<Boolean>()
    val filterParameterName: LiveData<Boolean>
        get() = _filterParameterName

    val settings = storage.getSettings.asLiveData()
    val viewCount = storage.getViewCount.asLiveData()
    val addCount = storage.getAddCount.asLiveData()

    fun showCarDetailInfo(id: Int, navController: NavController) {
        viewModelScope.launch {
            storage.saveViewCount(storage.getViewCount.first() - 1)
        }
        navController.navigate(CarsListFragmentDirections.actionCarsListFragmentToDetailInfoFragment(
            id))
    }

    fun moveToAddNewCarScreen(navController: NavController) {
        viewModelScope.launch {
            storage.saveAddCount(storage.getAddCount.first() - 1)
        }
        navController.navigate(R.id.action_carsListFragment_to_newCarFragment)
    }

    fun moveToSettingsScreen(navController: NavController) {
        navController.navigate(R.id.action_carsListFragment_to_settingsFragment)
    }

    fun setSubscription() {
        viewModelScope.launch(Dispatchers.IO) {
            var subscriptionTime = LocalDateTime.now()
            subscriptionTime = subscriptionTime.plusMinutes(1)
            storage.saveSettings(subscriptionTime.toEpochSecond(ZoneOffset.UTC).toString())
        }
    }

    fun setFilter(directionDown: Boolean, nameParam: Boolean) {
        _filterDirectionDown.value = directionDown
        _filterParameterName.value = nameParam
    }

}
