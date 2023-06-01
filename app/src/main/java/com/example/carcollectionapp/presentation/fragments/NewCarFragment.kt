package com.example.carcollectionapp.presentation.fragments

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.carcollectionapp.CarApp
import com.example.carcollectionapp.R
import com.example.carcollectionapp.databinding.FragmentNewCarBinding
import com.example.carcollectionapp.domain.CarInfo
import com.example.carcollectionapp.presentation.viewmodel.NewCarViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject


class NewCarFragment : BaseFragment<FragmentNewCarBinding, NewCarViewModel>() {

    companion object {
        private const val GALLERY = 1
        private const val CAMERA = 2
        private const val IMAGE_DIRECTORY = "cars"
    }
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener

    @Inject
    lateinit var calendar: Calendar

    @Inject
    lateinit var theNewCar: CarInfo

    private val component by lazy {
        (requireActivity().application as CarApp).component
    }

    override fun injectDependencies() {
        component.inject(this)
    }

    override fun getViewBinding(): FragmentNewCarBinding {
        return FragmentNewCarBinding.inflate(layoutInflater)
    }

    override fun getViewModelClass(): Class<NewCarViewModel> {
        return NewCarViewModel::class.java
    }

    override fun setupView() {
        setupDatePickerListener()
        setupClickListeners()
    }

    private fun setupDatePickerListener() {
        dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = resources.getString(R.string.date_format)
            val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
            binding.etProductionDate.setText(sdf.format(calendar.time).toString())
        }
    }

    private fun setupClickListeners() {

        setupAddImageClickListener()
        setupDateChooseClickListener()
        setupSaveButtonListener()

    }

    private fun setupAddImageClickListener() {
        binding.tvAddImage.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(requireActivity())
            pictureDialog.setTitle(resources.getString(R.string.choose_action))
            val pictureDialogItems = arrayOf(resources.getString(R.string.choose_photo), resources.getString(R.string.choose_gallery))
            pictureDialog.setItems(pictureDialogItems) { _, which ->
                when (which) {
                    0 -> {
                        choosePhotoFromGallery()
                    }
                    1 -> {
                        takePhotoFromCamera()
                    }
                }
            }
            pictureDialog.show()
        }
    }

    private fun setupDateChooseClickListener() {
        binding.etProductionDate.setOnClickListener {
            DatePickerDialog(requireActivity(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun setupSaveButtonListener(){
        binding.btnSave.setOnClickListener {
            theNewCar.carName = binding.etName.text.toString()
            theNewCar.engineCapacity = binding.etPower.text.toString().toIntOrNull() ?: 0
            theNewCar.productionDate = binding.etProductionDate.text.toString()
            theNewCar.insertionDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern(resources.getString(R.string.date_format)))

            viewModel.saveNewCar(theNewCar, findNavController())
        }
    }

    private fun choosePhotoFromGallery() {
        Dexter.withContext(requireActivity()).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report!!.areAllPermissionsGranted()) {
                    val galleryIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    requireActivity().startActivityForResult(galleryIntent, GALLERY)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>,
                token: PermissionToken,
            ) {
                showRationalDialogForPermissions()
            }
        }).onSameThread().check();
    }

    private fun takePhotoFromCamera() {
        Dexter.withActivity(requireActivity()).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report!!.areAllPermissionsGranted()) {
                    val cameraIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, CAMERA)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>,
                token: PermissionToken,
            ) {
                showRationalDialogForPermissions()
            }
        }).onSameThread().check();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY) {
                if (data != null) {
                    val contentURI = data.data
                    try {
                        val selectedImageBitmap =
                            MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,
                                contentURI)
                        binding.ivImage.setImageBitmap(selectedImageBitmap)
                        theNewCar.picturePath = saveImageToInternalStorage(selectedImageBitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(requireActivity(), resources.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else if (requestCode == CAMERA) {
                val thumbNail: Bitmap = data?.extras?.get("data") as Bitmap
                binding.ivImage.setImageBitmap(thumbNail)
                theNewCar.picturePath = saveImageToInternalStorage(thumbNail)
            }
        }
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(requireActivity())
            .setMessage(resources.getString(R.string.require_permissions))
            .setPositiveButton(resources.getString(R.string.confirm)) { dialog, _ ->
                dialog.dismiss()
            }.setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }.show()
    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        val wrapper = ContextWrapper(requireActivity())
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file.absolutePath
    }

}