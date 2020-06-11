package com.fdmt.walmart.presentation.stores.list

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fdmt.walmart.domain.base.Resource
import com.fdmt.walmart.domain.base.Response
import com.fdmt.walmart.domain.base.Status
import com.fdmt.walmart.domain.stores.entity.Store
import com.fdmt.walmart.presentation.R
import com.fdmt.walmart.presentation.base.BaseFragment
import com.fdmt.walmart.presentation.extension.gone
import com.fdmt.walmart.presentation.extension.visible
import com.fdmt.walmart.presentation.stores.StoresSharedViewModel
import kotlinx.android.synthetic.main.fragment_store_list.*
import org.koin.androidx.viewmodel.ext.android.getViewModel


class StoreListFragment : BaseFragment() {

    private val TAG = "StoreListFragment"
    override var fragmentLayout: Int = R.layout.fragment_store_list
    private val storesSharedViewModel by lazy { requireParentFragment().getViewModel<StoresSharedViewModel>() }
    private val adapter = StoresAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_store_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.getItemId()

        if (id == R.id.action_map) {
            storesSharedViewModel.changePage(1)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupViewModel()
    }

    private fun setupRecycler() {
        stores_recyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        stores_recyclerview.adapter = adapter
    }

    private fun setupViewModel() {
        storesSharedViewModel.stores.observe(viewLifecycleOwner, observer)
    }

    private val observer = Observer<Resource<List<Response>>> {
        when (it.status) {
            Status.SUCCESS -> addProducts(it.data as List<Store>?)
            Status.ERROR -> showError(it.message!!)
            Status.LOADING -> showLoading()
        }
    }

    private fun showError(message: String) {
        progress.gone()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun addProducts(productsList: List<Store>?) {
        Log.d(TAG, "addProducts: ")
        progress.gone()
        adapter.submitList(productsList)
        adapter.notifyDataSetChanged()
    }

    private fun showLoading() {
        progress.visible()
    }

}