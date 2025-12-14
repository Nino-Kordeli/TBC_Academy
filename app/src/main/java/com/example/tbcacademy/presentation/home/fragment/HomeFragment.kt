package com.example.tbcacademy.presentation.home.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.example.tbcacademy.R
import com.example.tbcacademy.common.BaseFragment
import com.example.tbcacademy.common.extensions.hide
import com.example.tbcacademy.common.extensions.show
import com.example.tbcacademy.databinding.FragmentHomeBinding
import com.example.tbcacademy.domain.model.Location
import com.example.tbcacademy.presentation.home.contract.HomeEvent
import com.example.tbcacademy.presentation.home.contract.HomeSideEffect
import com.example.tbcacademy.presentation.home.vm.HomeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    OnMapReadyCallback {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var googleMap: GoogleMap
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    private var hasShownInitialLocation = false

    private val permissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            binding.locationRequiredContainer.hide()
            hasShownInitialLocation = false
            setupMap()
        } else {
            binding.locationRequiredContainer.show()
        }
    }

    override fun bind() {
        setupBottomSheet()
        setupButtons()
        checkPermission()
        observeState()
        observeSideEffect()
    }

    private fun checkPermission() = with(binding) {
        if (hasPermission()) {
            locationRequiredContainer.hide()
            setupMap()
        } else {
            locationRequiredContainer.show()
        }
    }

    private fun hasPermission(): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun setupMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as? SupportMapFragment

        if (mapFragment == null) {
            return
        }

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        setupMapUi()
        setupMarkerClicks()
        viewModel.onEvent(HomeEvent.LoadLocations)
    }

    @SuppressLint("MissingPermission")
    private fun setupMapUi() {
        googleMap.uiSettings.isZoomControlsEnabled = false
        if (hasPermission()) {
            googleMap.isMyLocationEnabled = true
        }
    }

    private fun setupMarkerClicks() {
        googleMap.setOnMarkerClickListener { marker ->
            (marker.tag as? Location)?.let(::showBottomSheet)
            true
        }
    }


    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collectLatest { state ->
                    binding.progressBar.visibility =
                        if (state.isLoading) View.VISIBLE else View.GONE

                    if (::googleMap.isInitialized) {
                        showMarkers(state.locations)

                        if (!hasShownInitialLocation && state.locations.isNotEmpty() && !state.isLoading) {
                            hasShownInitialLocation = true
                            showBottomSheet(state.locations.first())
                        }
                    }
                }
            }
        }
    }

    private fun observeSideEffect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.sideEffect.collectLatest { sideEffect ->
                    when (sideEffect) {
                        is HomeSideEffect.Error -> {

                        }
                    }
                }
            }
        }
    }

    private fun showMarkers(locations: List<Location>) {
        googleMap.clear()

        locations.forEach { location ->
            val marker = googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(location.latitude, location.longitude))
                    .title(location.title)
            )
            marker?.tag = location
        }

        locations.firstOrNull()?.let {
            googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 12f)
            )
        }
    }

    private fun setupButtons() = with(binding) {
        btnZoomIn.setOnClickListener {
            if (::googleMap.isInitialized) {
                googleMap.animateCamera(CameraUpdateFactory.zoomIn())
            }
        }
        btnZoomOut.setOnClickListener {
            if (::googleMap.isInitialized) {
                googleMap.animateCamera(CameraUpdateFactory.zoomOut())
            }
        }
        btnShowAll.setOnClickListener { showAllLocations() }
        btnEnableLocation.setOnClickListener {
            permissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun showAllLocations() {
        if (!::googleMap.isInitialized) return

        val locations = viewModel.state.value.locations
        if (locations.isEmpty()) return

        val boundsBuilder = LatLngBounds.Builder()
        locations.forEach { location ->
            boundsBuilder.include(LatLng(location.latitude, location.longitude))
        }

        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 100)
        )
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun showBottomSheet(location: Location) = with(binding) {
        tvLocationTitle.text = location.title
        tvLocationDescription.text = location.description
        imgLocation.load(location.imageUrl)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}