package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.data.network.ResponseState
import com.picpay.desafio.android.domain.repository.ContactsRepository

class ContactsRepositoryImpl(
    private val picPayService: PicPayService
): ContactsRepository {
    override suspend fun getContacts(): ResponseState {
        return try {
            val response = picPayService.getContacts()
            ResponseState.Success(response)
        }catch (e: Throwable) {
            ResponseState.Error(e)
        }
    }
}