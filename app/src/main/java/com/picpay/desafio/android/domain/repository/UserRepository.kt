package com.picpay.desafio.android.domain.repository

import com.picpay.desafio.android.data.network.ResponseState

interface UserRepository {
    suspend fun getUsers(): ResponseState
}