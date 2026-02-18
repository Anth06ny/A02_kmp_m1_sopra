package com.example.a02_kmp_m1_sopra

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform