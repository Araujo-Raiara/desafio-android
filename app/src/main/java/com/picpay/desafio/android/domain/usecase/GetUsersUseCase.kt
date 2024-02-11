package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.data.network.ResponseState
import com.picpay.desafio.android.domain.repository.UserRepository

class GetUsersUseCase(
    private val userRepository: UserRepository
) {

    suspend fun getUsers(): ResponseState {
        return userRepository.getUsers()
    }
}