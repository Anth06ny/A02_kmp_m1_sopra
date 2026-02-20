package com.example.a02_kmp_m1_sopra.di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.example.a02_kmp_m1_sopra.db.MyDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

//koin.desktop.kt (dans jvmMain)
actual fun databaseModule(): Module = module {
    single {
        val dbFile = File("myDatabase.db")
        val driver = JdbcSqliteDriver("jdbc:sqlite:${dbFile.name}")
        if (!dbFile.exists()) {
            MyDatabase.Schema.create(driver)
        }
        MyDatabase(driver)
    }
}