package com.fdmt.walmart.presentation.stores

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fdmt.walmart.presentation.R
import com.fdmt.walmart.presentation.base.BaseFragment
import com.fdmt.walmart.presentation.stores.list.StoreListFragment
import com.fdmt.walmart.presentation.stores.map.StoreMapFragment
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.android.synthetic.main.fragment_store_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val NUM_PAGES = 2

class StoreMainFragment : BaseFragment() {

    private val TAG = "StoreMainFragment"
    override var fragmentLayout: Int = R.layout.fragment_store_main

    val storesSharedViewModel: StoresSharedViewModel by viewModel()
    val fusedLocationClient: FusedLocationProviderClient by inject()
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        stores_view_pager.adapter = pagerAdapter
        stores_view_pager.isUserInputEnabled = false

        checkPermissions()
        setupToolbar()
        setUpObservers()
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    storesSharedViewModel.updateLocation(location)
                }
            }
    }

    private fun setUpObservers() {

        storesSharedViewModel.pageChange.observe(viewLifecycleOwner, pageChangeObserver)

    }

    private val pageChangeObserver = Observer<Int> {
        stores_view_pager.currentItem = it
    }

    private fun setupToolbar() {

        requireActivity().setTitle("Store Finder")

    }

    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLocation()
                } else {
                    checkPermissions()
                }
                return
            }
        }
    }

    private inner class ScreenSlidePagerAdapter(f: Fragment) : FragmentStateAdapter(f) {
        override fun getItemCount(): Int =
            NUM_PAGES

        override fun createFragment(position: Int): Fragment {

            return when (position) {
                0 -> StoreListFragment()
                1 -> StoreMapFragment()
                else -> {
                    StoreListFragment()
                }
            }
        }
    }

}