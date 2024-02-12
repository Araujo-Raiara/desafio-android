package com.picpay.desafio.android.data.network

import com.picpay.desafio.android.data.entity.Contact

sealed class ResponseState {
    data class Success(val contactsList: List<Contact>) : ResponseState()
    data class Error(val error: Throwable) : ResponseState()
   data object Loading : ResponseState()
}