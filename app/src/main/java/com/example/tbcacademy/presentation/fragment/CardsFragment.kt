package com.example.tbcacademy.presentation.fragment

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.common.Resource
import com.example.tbcacademy.databinding.FragmentCardsBinding
import com.example.tbcacademy.presentation.adapter.CardsAdapter
import com.example.tbcacademy.presentation.vm.CardsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardsFragment : BaseFragment<FragmentCardsBinding>(FragmentCardsBinding::inflate) {

    private val viewModel: CardsViewModel by viewModels()
    private val adapter = CardsAdapter()

    override fun bind() {
        binding.bottomNavigation.selectedItemId = R.id.home
        setupCarousel()
    }

    private fun setupCarousel() {
        binding.carousel.adapter = adapter
        binding.carousel.offscreenPageLimit = 3

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.page_margin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)

        binding.carousel.setPageTransformer { page, position ->
            val scale = 0.85f + (1 - kotlin.math.abs(position)) * 0.15f
            page.scaleX = scale
            page.scaleY = scale
            page.alpha = 0.6f + (1 - kotlin.math.abs(position)) * 0.4f

            val offset = position * -(2 * offsetPx + pageMarginPx)
            page.translationX = offset
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cardsState.collect { state ->
                    when (state) {
                        is Resource.Loading -> {
                        }

                        is Resource.Success -> {
                            adapter.submitList(state.data)
                        }

                        is Resource.Error -> {
                            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        viewModel.getCards()
    }
}
