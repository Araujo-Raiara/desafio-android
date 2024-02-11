package com.picpay.desafio.android.data.network

import com.picpay.desafio.android.data.entity.User

sealed class ResponseState {
    data class Success(val usersList: List<User>) : ResponseState()
    data class Error(val error: Throwable) : ResponseState()
   data object Loading : ResponseState()
}