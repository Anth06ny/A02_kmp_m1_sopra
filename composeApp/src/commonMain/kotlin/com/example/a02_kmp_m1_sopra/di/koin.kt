package com.example.a02_kmp_m1_sopra.di

import com.example.a02_kmp_m1_sopra.data.remote.KtorPhotographersAPI
import com.example.a02_kmp_m1_sopra.data.remote.KtorPhotographersFakeAPI
import com.example.a02_kmp_m1_sopra.domaine.remote.IKtorPhotographerAPI
import com.example.a02_kmp_m1_sopra.presentation.viewmodel.MainViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

//Si besoin du contexte, pour le passer en paramètre au lancement de Koin
fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(apiModule, viewModelModule)
    }.koin

// Version pour iOS et Desktop
fun initKoin() = initKoin {}

val apiModule = module {
   single {
       HttpClient {
           install(ContentNegotiation) {
               json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
           }
           install(HttpTimeout) {
               requestTimeoutMillis = 5000
           }
           //Proxy
           //engine {
           //    proxy = ProxyBuilder.http("monproxy:1234")
           //}
       }
   }

    single<IKtorPhotographerAPI>{
        KtorPhotographersAPI(get())
    }

    //singleOf<IKtorPhotographerAPI>(::KtorPhotographersAPI)
}

val apiFakeModule = module {

    single<IKtorPhotographerAPI>{
        KtorPhotographersFakeAPI()
    }

    //singleOf<IKtorPhotographerAPI>(::KtorPhotographersAPI)
}

val viewModelModule = module {
    viewModelOf(::MainViewModel)
}