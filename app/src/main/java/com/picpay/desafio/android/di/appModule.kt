package com.picpay.desafio.android.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.network.PicPayService
import com.picpay.desafio.android.data.repository.ContactsRepositoryImpl
import com.picpay.desafio.android.domain.repository.ContactsRepository
import com.picpay.desafio.android.domain.usecase.FilterListByInputUseCase
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
    single { provideService(get()) }
    single { provideRetrofit() }
    single<ContactsRepository> { ContactsRepositoryImpl(get()) }
    single { GetContactsUseCase(get()) }
    single { FilterListByInputUseCase(get()) }
    viewModel { ContactsViewModel(get(), get(), get()) }
}

private fun provideRetrofit() : Retrofit {
    val gson: Gson = GsonBuilder().create()
    val loggingInterceptor = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }
    val okHttp: OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
}

fun provideService(retrofit: Retrofit) : PicPayService = retrofit.create(PicPayService::class.java)