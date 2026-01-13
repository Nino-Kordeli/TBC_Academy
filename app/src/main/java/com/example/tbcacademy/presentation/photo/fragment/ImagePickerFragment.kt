package com.example.tbcacademy.presentation.photo.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbcacademy.databinding.BottomSheetImagePickerBinding
import com.example.tbcacademy.databinding.FragmentPhotoBinding
import com.example.tbcacademy.presentation.common.BaseFragment
import com.example.tbcacademy.presentation.photo.contract.ImagePickerEvent
import com.example.tbcacademy.presentation.photo.contract.ImagePickerSideEffect
import com.example.tbcacademy.presentation.photo.vm.ImagePickerViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File

@AndroidEntryPoint
class ImagePickerFragment : BaseFragment<FragmentPhotoBinding>(FragmentPhotoBinding::inflate) {

    private val viewModel: ImagePickerViewModel by viewModels()
    private var photoUri: Uri? = null

    private val pickImageFromGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                viewModel.onEvent(ImagePickerEvent.ImageSelected(it))
                compressAndUpload(it)
            }
        }

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                photoUri?.let {
                    viewModel.onEvent(ImagePickerEvent.ImageSelected(it))
                    compressAndUpload(it)
                }
            }
        }

    private val requestCameraPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Camera permission is required",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun bind() {
        binding.btnAddPhoto.setOnClickListener {
            showImagePickerBottomSheet()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.progressBar.isVisible = state.loading
                    state.selectedImageUri?.let { binding.ivSelectedImage.setImageURI(it) }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { sideEffect ->
                    when (sideEffect) {
                        is ImagePickerSideEffect.ShowToast ->
                            Toast.makeText(requireContext(), sideEffect.message, Toast.LENGTH_SHORT)
                                .show()
                    }
                }
            }
        }
    }

    private fun showImagePickerBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())

        val sheetBinding =
            BottomSheetImagePickerBinding.inflate(layoutInflater)

        bottomSheetDialog.setContentView(sheetBinding.root)

        sheetBinding.layoutCamera.setOnClickListener {
            bottomSheetDialog.dismiss()
            checkCameraPermissionAndOpen()
        }

        sheetBinding.layoutGallery.setOnClickListener {
            bottomSheetDialog.dismiss()
            pickImageFromGallery.launch("image/*")
        }

        sheetBinding.btnCancel.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }


    private fun checkCameraPermissionAndOpen() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }

            else -> {
                requestCameraPermission.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCamera() {
        val photoFile = File(
            requireContext().cacheDir,
            "photo_${System.currentTimeMillis()}.jpg"
        )
        photoUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            photoFile
        )
        takePicture.launch(photoUri)
    }

    private fun compressAndUpload(uri: Uri) {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        }

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val bytes = stream.toByteArray()
        viewModel.onEvent(ImagePickerEvent.UploadImage(bytes))
    }
}