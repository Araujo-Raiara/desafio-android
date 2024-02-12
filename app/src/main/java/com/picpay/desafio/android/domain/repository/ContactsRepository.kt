package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.data.entity.Contact
import com.picpay.desafio.android.data.network.ResponseState

interface ContactsRepository {
    suspend fun getContactsFromApi(): ResponseState
    fun getLatestContacts() : List<Contact>
}