package com.picpay.desafio.android.core

import android.app.Application
import com.picpay.desafio.android.di.picPayModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PicPayApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PicPayApplication)
            modules(picPayModule)
        }
    }
}