package com.example.carcollectionapp.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.carcollectionapp.R
import com.example.carcollectionapp.domain.CarInfo

class CarListAdapter : ListAdapter<CarInfo, CarInfoViewHolder>(CarDiffUtilCallBack()) {

    var itemOnClickListener: ((CarInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_card_view, parent, false)
        return CarInfoViewHolder(view)
    }


    override fun onBindViewHolder(holder: CarInfoViewHolder, position: Int) {
        val car = getItem(position)
        holder.setImage(car.picturePath)
        holder.setPower(car.engineCapacity)
        holder.setName(car.carName)

        holder.view.setOnClickListener{
            itemOnClickListener?.invoke(car)
        }
    }
}