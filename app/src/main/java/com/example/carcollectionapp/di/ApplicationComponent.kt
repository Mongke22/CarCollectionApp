package com.example.carcollectionapp.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.carcollectionapp.presentation.fragments.*
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        PresentationModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: NewCarFragment)

    fun inject(fragment: CarsListFragment)

    fun inject(fragment: SettingsFragment)

    fun inject(fragment: DetailInfoFragment)



    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}