package com.example.a02_kmp_m1_sopra

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {

    Window(
        onCloseRequest = ::exitApplication,
        title = "A02_kmp_m1_sopra",
    ) {
        App()
    }
}