package com.picpay.desafio.android.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    @SerializedName("img") val img: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("username") val username: String? = null
) : Parcelable