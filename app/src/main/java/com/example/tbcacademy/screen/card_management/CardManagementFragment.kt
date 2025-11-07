package com.example.tbcacademy.screen.card_management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentCardManagementBinding
import com.example.tbcacademy.screen.card_management.adapter.CardAdapter
import com.example.tbcacademy.screen.card_management.adapter.CardPaymentMethodAdapter
import com.example.tbcacademy.screen.card_management.model.Card
import com.example.tbcacademy.screen.card_management.vm.CardViewModel
import com.example.tbcacademy.screen.card_management.vm.PaymentMethodViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CardManagementFragment : BaseFragment<FragmentCardManagementBinding>() {

    private val cardViewModel: CardViewModel by activityViewModels()
    private val paymentViewModel: PaymentMethodViewModel by viewModels()
    private lateinit var cardAdapter: CardAdapter
    private lateinit var paymentAdapter: CardPaymentMethodAdapter

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentCardManagementBinding {
        return FragmentCardManagementBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardAdapter = CardAdapter { showDeleteConfirmationBottomSheet(it) }
        binding.vpCards.adapter = cardAdapter

        paymentAdapter = CardPaymentMethodAdapter()
        binding.rvPaymentMethod.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = paymentAdapter
        }

        binding.tvAddNewCard.setOnClickListener {
            findNavController().navigate(R.id.action_cardManagement_to_addNewCard)
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            cardViewModel.cards.collectLatest {
                cardAdapter.submitList(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            paymentViewModel.cards.collectLatest {
                paymentAdapter.submitList(it)
            }
        }
    }

    private fun showDeleteConfirmationBottomSheet(card: Card) {
        BottomSheetDialog(requireContext()).apply {
            setContentView(R.layout.bottom_sheet_delete_card)
            findViewById<View>(R.id.btnYes)?.setOnClickListener {
                cardViewModel.deleteCard(card)
                dismiss()
            }
            findViewById<View>(R.id.btnNo)?.setOnClickListener { dismiss() }
            show()
        }
    }
}