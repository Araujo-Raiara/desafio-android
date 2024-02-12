package com.picpay.desafio.android.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.data.repository.ContactsRepositoryImpl
import com.picpay.desafio.android.domain.repository.ContactsRepository
import com.picpay.desafio.android.domain.usecase.GetContactsUseCase
import com.picpay.desafio.android.presenter.viewmodel.ContactsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

val picPayModule = module {
    single<ContactsRepository> { ContactsRepositoryImpl(get()) }
    viewModel { ContactsViewModel(get()) }
    factory { GetContactsUseCase(get()) }
    single { providesService() }

}

private fun providesService() {
    val gson: Gson = GsonBuilder().create()
    val loggingInterceptor = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }
    val okHttp: OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    retrofit.create(PicPayService::class.java)
}