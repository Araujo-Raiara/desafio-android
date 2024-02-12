package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.entity.Contact
import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.data.network.ResponseState
import com.picpay.desafio.android.domain.repository.ContactsRepository

class ContactsRepositoryImpl(
    private val picPayService: PicPayService
): ContactsRepository {

    private var contactsList : List<Contact> = emptyList()
    override suspend fun getContactsFromApi(): ResponseState {
        return try {
            val response = picPayService.getContacts()
            contactsList = response
            ResponseState.Success(response)
        }catch (e: Throwable) {
            ResponseState.Error(e)
        }
    }

    override fun getLatestContacts() : List<Contact> {
        return contactsList
    }
}