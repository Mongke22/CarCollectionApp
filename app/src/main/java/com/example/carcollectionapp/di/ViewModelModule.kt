package com.example.carcollectionapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.carcollectionapp.presentation.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CarsListViewModel::class)
    fun bindCarsListViewModel(viewModel: CarsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailInfoViewModel::class)
    fun bindDetailInfoViewModel(viewModel: DetailInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewCarViewModel::class)
    fun bindNewCarViewModel(viewModel: NewCarViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel
}