package com.picpay.desafio.android.data.network

import android.os.Parcelable
import com.picpay.desafio.android.data.entity.Contact
import kotlinx.android.parcel.Parcelize

sealed class ResponseState : Parcelable {
   @Parcelize data class Success(val contactsList: List<Contact>) : ResponseState()
    @Parcelize data class Error(val error: Throwable) : ResponseState()
   @Parcelize data object Loading : ResponseState()
}