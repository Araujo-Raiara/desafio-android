package com.picpay.desafio.android.presenter.adapter

import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.data.entity.Contact
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.gone
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListItemViewHolder(
    private val item: ListItemUserBinding
) : RecyclerView.ViewHolder(item.root) {

    fun bind(contact: Contact) {
        item.apply {
            name.text = contact.name
            username.text = contact.username
            progressBar.gone()
        }
        Picasso.get()
            .load(contact.img)
            .error(R.drawable.ic_round_account_circle)
            .into(item.picture, object : Callback {
                override fun onSuccess() {
                    item.progressBar.gone()
                }

                override fun onError(e: Exception?) {
                    item.progressBar.gone()
                }
            })
    }
}