package com.fdmt.walmart.presentation.stores.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fdmt.walmart.domain.stores.entity.Store
import com.fdmt.walmart.presentation.R
import kotlinx.android.synthetic.main.item_store.view.*


/*class StoresAdapter : ListAdapter<Store, StoresAdapter.ViewHolder>(StoresAdapter) {

    companion object : DiffUtil.ItemCallback<Store>() {
        override fun areItemsTheSame(oldItem: Store, newItem: Store): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: Store, newItem: Store): Boolean =
            oldItem.storeID == newItem.storeID
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentStore = getItem(position)
        holder.binding.store = currentStore
        holder.binding.executePendingBindings()
    }

    class ViewHolder(val binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemStoreBinding.inflate(layoutInflater)

        return StoresAdapter.ViewHolder(binding)
    }

}*/

class StoresAdapter : ListAdapter<Store, StoresAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_store, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Store) = with(itemView) {

            this.txt_store_name.text = item.storeName
            this.txt_store_address.text = item.address
            this.txt_store_opens.text = item.opens
            this.txt_store_telephone.text = item.telephone

            setOnClickListener {
                // TODO: Handle on click
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Store>() {
    override fun areItemsTheSame(oldItem: Store, newItem: Store): Boolean {
        return oldItem.storeID == newItem.storeID
    }

    override fun areContentsTheSame(oldItem: Store, newItem: Store): Boolean {
        return oldItem == newItem
    }
}