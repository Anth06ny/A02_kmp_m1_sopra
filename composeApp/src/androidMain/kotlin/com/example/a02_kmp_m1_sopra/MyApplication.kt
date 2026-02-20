package com.example.a02_kmp_m1_sopra

import android.app.Application
import com.example.a02_kmp_m1_sopra.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin { androidContext(this@MyApplication) }
    }
}