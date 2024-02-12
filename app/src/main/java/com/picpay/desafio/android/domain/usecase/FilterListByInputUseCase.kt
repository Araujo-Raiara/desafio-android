package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.data.network.ResponseState
import com.picpay.desafio.android.domain.repository.ContactsRepository

private const val DELIMITER = " "

class FilterListByInputUseCase(
    private val contactsRepository: ContactsRepository
) {

    operator fun invoke(textInput: String): ResponseState {
        val contactsList = contactsRepository.getLatestContacts()
        if (textInput.isBlank()) {
            return ResponseState.Success(contactsList)
        }
        val filteredList = contactsList.filter {
            (it.name?.split(DELIMITER)?.any { fraction ->
                fraction.lowercase().startsWith(textInput.lowercase()) } ?: false) ||
                    (it.username?.lowercase()?.startsWith(textInput.lowercase()) ?: false)
        }
        return ResponseState.Success(filteredList)
    }
}