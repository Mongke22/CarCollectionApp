package com.example.carcollectionapp.presentation.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.carcollectionapp.domain.CarInfo

class CarDiffUtilCallBack: DiffUtil.ItemCallback<CarInfo>() {
    override fun areItemsTheSame(oldItem: CarInfo, newItem: CarInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CarInfo, newItem: CarInfo): Boolean {
        return oldItem == newItem
    }
}