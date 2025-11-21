package com.example.tbcacademy.presentation.screens.contacts

import ContactsAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.databinding.FragmentContactsBinding
import com.example.tbcacademy.presentation.screens.contacts.vm.ContactsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactsFragment : BaseFragment<FragmentContactsBinding, ContactsViewModel>() {

    override val viewModel: ContactsViewModel by viewModels()
    private val adapter = ContactsAdapter()

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentContactsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupSearch()
        observeMessages()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setupSearch() {
        binding.btnFilter.setOnClickListener {
            val query = binding.etSearchField.text.toString()
            viewModel.onSearch(query)
            userNotFound()
        }
    }

    private fun userNotFound() {
        if (viewModel.messages.value.isEmpty()) {
            Snackbar.make(binding.root, "User not found", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun observeMessages() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.messages.collect { list ->
                    adapter.submitList(list)
                }
            }
        }
    }
}
