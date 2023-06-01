package com.example.carcollectionapp.presentation.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.carcollectionapp.databinding.SubscriptionCardViewBinding

class SubscriptionDialog: DialogFragment() {
    var onApplyFunc: (() -> Unit)? = null

    private var _binding: SubscriptionCardViewBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentBinding is null")


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            _binding = SubscriptionCardViewBinding.inflate(layoutInflater)


            builder
                .setView(binding.root)
                .setPositiveButton("Купить"
                ) { dialog, _ ->
                    onApplyFunc?.invoke()
                    dialog.cancel()
                }
                .setNegativeButton("Нет, спасибо"){ dialog, _ ->
                    dialog.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}