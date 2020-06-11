package com.fdmt.walmart.presentation.stores.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.fdmt.walmart.domain.base.Resource
import com.fdmt.walmart.domain.base.Response
import com.fdmt.walmart.domain.base.Status
import com.fdmt.walmart.domain.stores.entity.Store
import com.fdmt.walmart.presentation.R
import com.fdmt.walmart.presentation.base.BaseFragment
import com.fdmt.walmart.presentation.stores.StoresSharedViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.getViewModel

class StoreMapFragment : BaseFragment(), ActivityCompat.OnRequestPermissionsResultCallback {

    private val TAG = "StoreListFragment"
    override var fragmentLayout: Int = R.layout.fragment_maps
    private val storesSharedViewModel by lazy { requireParentFragment().getViewModel<StoresSharedViewModel>() }

    lateinit var googleMap: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.getUiSettings().isZoomControlsEnabled = false
        googleMap.getUiSettings().isMyLocationButtonEnabled = true
        googleMap.getUiSettings().isRotateGesturesEnabled = false
        this.googleMap = googleMap
        setupObservers()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_store_map, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.getItemId()

        if (id == R.id.action_list) {
            storesSharedViewModel.changePage(0)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeMap()
    }

    private fun setupObservers() {
        storesSharedViewModel.locationUpdates.observe(viewLifecycleOwner, locationChangeObserver)
        storesSharedViewModel.stores.observe(viewLifecycleOwner, storesObserver)
    }

    private val storesObserver = Observer<Resource<List<Response>>> {
        when (it.status) {
            Status.SUCCESS -> addMarkers(it.data as List<Store>)
            Status.ERROR -> showError(it.message!!)
            Status.LOADING -> showLoading()
        }
    }

    @SuppressLint("MissingPermission")
    private val locationChangeObserver = Observer<LatLng> {
        if (this::googleMap.isInitialized) {
            googleMap.isMyLocationEnabled = true
            moveToLocation(it)
        }
    }

    private fun initializeMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun moveToLocation(locationLatLng: LatLng) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 15f))
    }

    private fun moveToBounds(bounds: LatLngBounds) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }

    private fun drawMarker(locationLatLng: LatLng, title: String) {
        googleMap.addMarker(
            MarkerOptions().position(locationLatLng)
                .title(title)
        )
    }

    private fun addMarkers(stores: List<Store>) {

        val markers = mutableListOf<LatLng>()

        stores.forEach {
            drawMarker(it.latLng, it.storeName)
            markers.add(it.latLng)
        }

        val builder = LatLngBounds.Builder()
        for (marker in markers) {
            builder.include(marker)
        }
        moveToBounds(builder.build())
    }

    private fun showLoading() {

    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

    }

}