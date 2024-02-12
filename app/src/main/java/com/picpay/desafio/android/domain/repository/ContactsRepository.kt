package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.data.network.ResponseState

interface ContactsRepository {
    suspend fun getContacts(): ResponseState
}