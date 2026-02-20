package com.example.a02_kmp_m1_sopra.domaine.remote

import com.example.a02_kmp_m1_sopra.data.remote.PhotographersDTO

interface IKtorPhotographerAPI {
    suspend fun loadPhotographers(): List<PhotographersDTO>
}
