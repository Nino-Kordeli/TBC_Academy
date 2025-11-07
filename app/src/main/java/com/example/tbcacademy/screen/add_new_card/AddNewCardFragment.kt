package com.example.tbcacademy.screen.add_new_card

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentAddNewCardBinding
import com.example.tbcacademy.screen.card_management.model.Card
import com.example.tbcacademy.screen.card_management.model.CardType
import com.example.tbcacademy.screen.card_management.vm.CardViewModel
import com.example.tbcacademy.utils.extensions.showSnackBar
import com.example.tbcacademy.utils.extensions.textValue
import com.google.android.material.snackbar.Snackbar
import com.squareup.moshi.Moshi
import java.io.File

class AddNewCardFragment : BaseFragment<FragmentAddNewCardBinding>() {

    private val cardViewModel: CardViewModel by activityViewModels()

    private val cardNumberWatcher = object : TextWatcher {
        private var updating = false

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable?) {
            if (updating) return
            updating = true

            val raw = s.toString()
            val digits = raw.filter { it.isDigit() }.take(16)
            val formatted = digits.chunked(4).joinToString(" ")

            if (raw != formatted) {
                binding.etCardNumberField.setText(formatted)
                binding.etCardNumberField.setSelection(formatted.length)
            }

            binding.layoutCardPreview.tvCardNumber.text =
                if (digits.isEmpty()) "**** **** **** ****" else formatted

            updating = false
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddNewCardBinding =
        FragmentAddNewCardBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDefaultPreview()
        setupDefaultCardType()
        setupClickListeners()
        setupTextWatchers()
    }

    private fun saveCardToJsonFile(card: Card) {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(Card::class.java)
        val json = adapter.toJson(card)

        try {
            val file = File(requireContext().filesDir, "cards.json")
            file.appendText(json + "\n")
            binding.root.showSnackBar("Card saved")
        } catch (e: Exception) {
            e.printStackTrace()
            binding.root.showSnackBar("Save failed", Snackbar.LENGTH_LONG)
        }
    }

    private fun setupDefaultPreview() = with(binding.layoutCardPreview) {
        tvCardHolderName.text = getString(R.string.card_holder)
        tvCardNumber.text = getString(R.string.stars_)
        tvValidThrough.text = getString(R.string.mm_yy_)
        ivCardImage.setImageResource(R.drawable.mastercard_card)
    }

    private fun setupDefaultCardType() {
        binding.rbMastercard.isChecked = true
    }

    private fun setupClickListeners() {
        binding.btnAddNewCard.setOnClickListener {
            if (validateInputs()) addCard()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.rgCardType.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbVisa -> binding.layoutCardPreview.ivCardImage
                    .setImageResource(R.drawable.visa_card)

                R.id.rbMastercard -> binding.layoutCardPreview.ivCardImage
                    .setImageResource(R.drawable.mastercard_card)
            }
        }
    }

    private fun setupTextWatchers() {
        binding.etCardNameField.doAfterTextChanged { editable ->
            val name = editable?.toString()?.takeIf { it.isNotBlank() } ?: "CARD HOLDER"
            binding.layoutCardPreview.tvCardHolderName.text = name.uppercase()
        }

        binding.etCardNumberField.addTextChangedListener(cardNumberWatcher)

        binding.etExpiryField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                val raw = s.toString().replace("/", "")
                if (raw.isEmpty()) return

                val monthInput = raw.take(2).toIntOrNull() ?: 0
                val yearPart = raw.drop(2)

                val clampedMonth = when {
                    monthInput > 12 -> 12
                    monthInput == 0 -> 1
                    else -> monthInput
                }

                val monthStr = if (raw.length == 1) {
                    clampedMonth.toString()
                } else {
                    clampedMonth.toString().padStart(2, '0')
                }

                val formatted = if (raw.length >= 2) {
                    "$monthStr/$yearPart"
                } else {
                    monthStr
                }

                if (s.toString() != formatted) {
                    binding.etExpiryField.setText(formatted)
                    binding.etExpiryField.setSelection(formatted.length)
                }
            }
        })
    }

    private fun validateInputs(): Boolean = with(binding) {
        etCardNameField.error = null
        etCardNumberField.error = null
        etExpiryField.error = null
        etCvv.error = null

        val name = etCardNameField.textValue().trim()
        val number = etCardNumberField.textValue().replace(" ", "")
        val expiry = etExpiryField.textValue()
        val cvv = etCvv.textValue()

        when {
            name.isEmpty() -> {
                etCardNameField.error = getString(R.string.enter_name); false
            }

            number.length != 16 -> {
                etCardNumberField.error = getString(R.string._16_digits); false
            }

            !expiry.matches(Regex("""\d{2}/\d{2}""")) -> {
                etExpiryField.error = getString(R.string._mm_yy_); false
            }

            cvv.length != 3 -> {
                etCvv.error = getString(R.string._3_digits); false
            }

            else -> true
        }
    }

    private fun addCard() {
        val type = if (binding.rbVisa.isChecked) CardType.VISA else CardType.MASTERCARD
        val bgRes =
            if (type == CardType.MASTERCARD) R.drawable.mastercard_card else R.drawable.visa_card

        val card = Card(
            cardHolder = binding.etCardNameField.textValue().trim(),
            cardNumber = binding.etCardNumberField.textValue().replace(" ", ""),
            expirationDate = binding.etExpiryField.textValue(),
            cvv = binding.etCvv.textValue(),
            cardType = type,
            backgroundRes = bgRes
        )

        cardViewModel.addCard(card)
        saveCardToJsonFile(card)
        findNavController().popBackStack()
    }
}
