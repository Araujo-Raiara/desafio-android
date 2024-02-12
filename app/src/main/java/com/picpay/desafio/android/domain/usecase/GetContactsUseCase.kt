package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.data.network.ResponseState
import com.picpay.desafio.android.domain.repository.ContactsRepository

class GetContactsUseCase(
    private val contactsRepository: ContactsRepository
) {
    suspend fun getContacts(): ResponseState {
        return contactsRepository.getContactsFromApi()
    }
}