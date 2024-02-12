package com.picpay.desafio.android.data.network

import com.picpay.desafio.android.data.entity.Contact
import retrofit2.http.GET


interface PicPayService {

    @GET("users")
    suspend fun getContacts(): List<Contact>
}