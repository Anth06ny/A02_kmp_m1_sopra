package com.example.a02_kmp_m1_sopra

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.a02_kmp_m1_sopra.di.initKoin

fun main() = application {

    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "A02_kmp_m1_sopra",
    ) {
        App()
    }
}