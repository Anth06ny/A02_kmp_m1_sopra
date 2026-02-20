package com.example.a02_kmp_m1_sopra.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.a02_kmp_m1_sopra.db.MyDatabase
import org.koin.dsl.module

//koin.android.kt (dans androidMain)
//Attention Android Studio vous générera databaseModule() : Module à ne pas confondre avec la fonction module
actual fun databaseModule() = module {
    single {
        //Penser à faire un Build -> "Compile all Sources" pour générer le MyDatabase
        val driver = AndroidSqliteDriver(MyDatabase.Schema, get(), "test.db")
        MyDatabase(driver)
    }
}