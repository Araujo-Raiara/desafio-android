package com.picpay.desafio.android.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.data.entity.Contact
import com.picpay.desafio.android.databinding.ListItemLoadingBinding
import com.picpay.desafio.android.databinding.ListItemUserBinding

private const val VIEW_TYPE_LOADING = 0
private const val VIEW_TYPE_USER = 1
private const val ONE_ITEM = 1

class UserListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var contacts = emptyList<Contact>()
        set(value) {
            isLoading = false
            val result = DiffUtil.calculateDiff(
                UserListDiffCallback(
                    field,
                    value
                )
            )
            result.dispatchUpdatesTo(this)
            field = value
        }

    fun isLoading() {
        isLoading = true
    }

    fun hideLoading() {
        isLoading = false
    }
    override fun getItemViewType(position: Int): Int {
        return if (isLoading) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_USER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = when(viewType) {
            VIEW_TYPE_LOADING -> LoadingListItemViewHolder(ListItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> UserListItemViewHolder(ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? UserListItemViewHolder)?.bind(contacts[position])
    }

    override fun getItemCount() : Int {
        return if(isLoading) {
            ONE_ITEM
        } else {
            contacts.size
        }
    }
}