package com.example.tbcacademy.screen.dialog

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.tbcacademy.databinding.FragmentDialogDetailsBinding
import com.example.tbcacademy.screen.model.DeliveryItem
import com.example.tbcacademy.screen.model.Status

class DeliveryDetailsDialog(
    private val item: DeliveryItem,
    private val onStatusChange: (DeliveryItem, Status) -> Unit
) : DialogFragment() {

    private var _binding: FragmentDialogDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        binding.apply {
            tvOrderIdDialogText.text = getString(com.example.tbcacademy.R.string.order)
            tvOrderIdDialog.text = item.orderId
            tvTrackingNumberDialogText.text = getString(com.example.tbcacademy.R.string.tracking_1)
            tvTrackingNumberDialog.text = item.trackingNumber
            tvPrice.text = item.price
            tvStatus.text = item.status.name

            btnDelivered.setOnClickListener {
                onStatusChange(item, Status.DELIVERED)
                dismiss()
            }

            btnCanceled.setOnClickListener {
                onStatusChange(item, Status.CANCELED)
                dismiss()
            }

            btnClose.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawableResource(R.color.transparent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}