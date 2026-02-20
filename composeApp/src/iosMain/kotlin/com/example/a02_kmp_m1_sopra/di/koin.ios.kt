package com.example.a02_kmp_m1_sopra.di

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.a02_kmp_m1_sopra.db.MyDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

//koin.iosmain.kt (dans iosMain)
actual fun databaseModule() = module {
    single {
        val driver = NativeSqliteDriver(MyDatabase.Schema, "test.db")
        MyDatabase(driver)
    }
}