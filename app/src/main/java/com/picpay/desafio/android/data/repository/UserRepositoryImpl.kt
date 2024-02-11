package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.data.entity.User
import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.data.network.ResponseState
import com.picpay.desafio.android.domain.repository.UserRepository

class UserRepositoryImpl(
    private val picPayService: PicPayService
): UserRepository {
    override suspend fun getUsers(): ResponseState {
        return try {
            val response = picPayService.getUsers()
            ResponseState.Success(response)
        }catch (e: Throwable) {
            ResponseState.Error(e)
        }
    }
}