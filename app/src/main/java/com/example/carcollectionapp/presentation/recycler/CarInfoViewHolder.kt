package com.example.carcollectionapp.presentation.recycler

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carcollectionapp.R

class CarInfoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private var name: TextView = view.findViewById(R.id.listCarName)
    private var image: ImageView = view.findViewById(R.id.listCarImage)
    private var power: TextView = view.findViewById(R.id.listCarPower)

    fun setName(carName: String) {
        name.text = carName
    }

    fun setImage(imagePath: String) {
        if (imagePath == "") {
            image.setImageResource(R.drawable.car_example)
        } else {
            image.setImageURI(Uri.parse(imagePath))
        }
    }

    fun setPower(carPower: Int) {
        power.text = String.format(view.resources.getString(R.string.car_power_template), carPower)
    }


}