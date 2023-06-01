package com.example.carcollectionapp.presentation.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.carcollectionapp.R
import com.example.carcollectionapp.databinding.FilterCardViewBinding


class FilterDialog(
    var filterDirectionDown: Boolean,
    var filterParameterName: Boolean
) : DialogFragment() {

    companion object{
        var allIsChecked = false
        var firstOptionChecked = false
        var secondOptionChecked = false
    }

    var onApplyFunc: (() -> Unit)? = null

    private var _binding: FilterCardViewBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentBinding is null")

    private fun initFilter() {
        if(firstOptionChecked){
            if(filterDirectionDown){
                binding.rbDirectionDown.isChecked = true
            }else{
                binding.rbDirectionUp.isChecked = true
            }
        }
        
        if(secondOptionChecked) {
            if (filterParameterName) {
                binding.rbOptionName.isChecked = true
            } else {
                binding.rbOptionPower.isChecked = true
            }
        }
    }

    private fun filterLogic() {
        setupFilterDirectionChangeListener()
        setupFilterParameterChangeListener()
    }

    private fun setupFilterDirectionChangeListener() {
        binding.rgFilterDirection.setOnCheckedChangeListener { _, buttonId ->
            firstOptionChecked = true
            filterDirectionDown =
                when (binding.rgFilterDirection.findViewById<RadioButton>(buttonId).text.toString()) {
                    requireContext().resources.getString(R.string.direction_down) -> {
                        true
                    }
                    else -> {
                        false
                    }
                }
        }
    }

    private fun setupFilterParameterChangeListener() {
        binding.rgFilterParameters.setOnCheckedChangeListener { _, buttonId ->
            secondOptionChecked = true
            filterParameterName =
                when (binding.rgFilterParameters.findViewById<RadioButton>(buttonId).text.toString()) {
                    requireContext().resources.getString(R.string.sort_option_name) -> {
                        true
                    }
                    else -> {
                        false
                    }
                }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            _binding = FilterCardViewBinding.inflate(layoutInflater)

            initFilter()
            filterLogic()

            builder
                .setView(binding.root)
                .setPositiveButton("Применить"
                ) { _, _ ->
                    if(checkState() || allIsChecked) {
                        onApplyFunc?.invoke()
                    }
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun checkState(): Boolean{
        allIsChecked = firstOptionChecked && secondOptionChecked
        return allIsChecked
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        if(!allIsChecked){
            firstOptionChecked = false
            secondOptionChecked = false
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


}