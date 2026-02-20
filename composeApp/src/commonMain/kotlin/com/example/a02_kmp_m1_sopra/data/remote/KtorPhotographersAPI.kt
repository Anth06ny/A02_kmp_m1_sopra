package com.example.a02_kmp_m1_sopra.data.remote

import com.example.a02_kmp_m1_sopra.BuildConfig
import com.example.a02_kmp_m1_sopra.di.initKoin
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin

@Serializable //KotlinX impose cette annotation
data class PhotographersDTO(
    val id: Int,
    val stageName: String,
    val photoUrl: String,
    val story: String,
    val portfolio: List<String>,
)

suspend fun main() {

    val koin = initKoin()

    val photographersAPI = koin.get<KtorPhotographersAPI>()

    println(photographersAPI.loadPhotographers().joinToString(separator = "\n\n"))

    //Pour que le programme s'arrête, inutile sur Android
    photographersAPI.close()
}

class KtorPhotographersAPI(val client: HttpClient) {
    companion object {
        private const val API_URL =
            "https://www.amonteiro.fr/api/photographers?apikey=${BuildConfig.PHOTOGRAPHER_API_KEY}"
    }

    //GET
    suspend fun loadPhotographers(): List<PhotographersDTO> {
        val response = client.get(API_URL) {
//            headers {
//                append("Authorization", "Bearer YOUR_TOKEN")
//                append("Custom-Header", "CustomValue")
//            }
        }
        if (!response.status.isSuccess()) {
            throw Exception("Erreur API: ${response.status} - ${response.bodyAsText()}")
        }
        return response.body()
    }



    fun close() {
        client.close()
    }
}