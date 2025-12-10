package com.example.tbcacademy.presentation.screen.feed.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.common.SyncEventBus
import com.example.tbcacademy.databinding.FragmentFeedBinding
import com.example.tbcacademy.presentation.screen.feed.adapter.PostAdapter
import com.example.tbcacademy.presentation.screen.feed.adapter.StoryAdapter
import com.example.tbcacademy.presentation.screen.feed.contract.FeedEvent
import com.example.tbcacademy.presentation.screen.feed.contract.FeedSideEffect
import com.example.tbcacademy.presentation.screen.feed.vm.FeedViewModel
import com.example.tbcacademy.presentation.service.DataSyncService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FeedFragment : BaseFragment<FragmentFeedBinding>(
    FragmentFeedBinding::inflate
) {

    private val viewModel: FeedViewModel by viewModels()

    private val storyAdapter by lazy { StoryAdapter() }
    private val postAdapter by lazy { PostAdapter() }

    @Inject
    lateinit var syncEventBus: SyncEventBus

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) startSyncService()
        else Toast.makeText(requireContext(),
            getString(R.string.notification_permission_denied), Toast.LENGTH_SHORT).show()
    }

    override fun bind() {
        setupRecyclerViews()
        setupClickListeners()
        observeState()
        observeSideEffects()
        observeSyncEvents()

        viewModel.onEvent(FeedEvent.LoadData)
    }

    private fun setupClickListeners() {
        binding.btnSync.setOnClickListener {
            checkNotificationPermissionAndStartService()
        }
    }

    private fun checkNotificationPermissionAndStartService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startSyncService()
            } else {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else {
            startSyncService()
        }
    }

    private fun startSyncService() {
        DataSyncService.start(requireContext())
        Toast.makeText(requireContext(),
            getString(R.string.background_sync_started), Toast.LENGTH_SHORT).show()
    }

    private fun setupRecyclerViews() {
        binding.rvStories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = storyAdapter
        }

        binding.rvPosts.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postAdapter
        }
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    storyAdapter.submitList(state.stories)
                    postAdapter.submitList(state.posts)

                    if (state.isLoading) binding.progress.show()
                    else binding.progress.hide()
                }
            }
        }
    }

    private fun observeSideEffects() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collect { effect ->
                    when (effect) {
                        is FeedSideEffect.ShowError ->
                            Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
                        FeedSideEffect.ShowLoading -> binding.progress.show()
                        FeedSideEffect.HideLoading -> binding.progress.hide()
                    }
                }
            }
        }
    }

    private fun observeSyncEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                syncEventBus.syncEvents.collect { result ->
                    result?.let {
                        if (it.success) {
                            Toast.makeText(
                                requireContext(),
                                getString(
                                    R.string.sync_complete_stories_posts,
                                    it.storiesCount,
                                    it.postsCount
                                ),
                                Toast.LENGTH_LONG
                            ).show()
                            viewModel.onEvent(FeedEvent.RefreshData)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.sync_failed_, it.errorMessage),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}
