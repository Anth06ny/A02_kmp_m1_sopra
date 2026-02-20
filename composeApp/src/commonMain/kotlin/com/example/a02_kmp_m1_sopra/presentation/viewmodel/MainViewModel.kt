package com.example.a02_kmp_m1_sopra.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a02_kmp_m1_sopra.data.remote.PhotographersDTO
import com.example.a02_kmp_m1_sopra.db.MyDatabase
import com.example.a02_kmp_m1_sopra.di.initKoin
import com.example.a02_kmp_m1_sopra.domaine.remote.IKtorPhotographerAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

suspend fun main() {

    val koin = initKoin()

    val viewModel = koin.get<MainViewModel>()
    viewModel.loadPhotographers()

    CoroutineScope(Dispatchers.Default).launch {

        viewModel.dataList.collect {
            println("collect : $it")
        }

    }



    println("list : " + viewModel.dataList.value)
    println("errorMessage : ${viewModel.errorMessage.value}")

    delay(10000)
}

class MainViewModel(val photographersAPI: IKtorPhotographerAPI, val myDatabase: MyDatabase) : ViewModel() {

    private val photographerQueries = myDatabase.photographerStorageQueries

    private val _dataList = MutableStateFlow(emptyList<PhotographersDTO>())
    val dataList = _dataList.asStateFlow()

    private val _runInProgress = MutableStateFlow(false)
    val runInProgress = _runInProgress.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

    private val jsonParser = Json { prettyPrint = true }

    init {
        loadPhotographers()
        //loadFakeData()
    }

    fun loadPhotographers() {

        viewModelScope.launch(Dispatchers.IO) {
            try {

                val list = photographersAPI.loadPhotographers()


                photographerQueries.transaction {
                    list.forEach { photographer ->
                        photographerQueries.insertOrReplacePhotographer(
                            photographer.id.toLong(),
                            photographer.stageName,
                            photographer.photoUrl,
                            photographer.story,
                            jsonParser.encodeToString(photographer.portfolio)
                        )
                    }
                }

                _dataList.value = list


            }
            catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = e.message ?: "Une erreur est survenue"

                _dataList.value = photographerQueries.selectAllPhotographers().executeAsList().map { photographer ->
                    PhotographersDTO(
                        id = photographer.id.toInt(),
                        stageName = photographer.stageName,
                        photoUrl = photographer.photoUrl,
                        story = photographer.story,
                        portfolio = jsonParser.decodeFromString(photographer.portfolio)
                    )
                }

            }
        }
    }

    //Pour la Preview
    fun loadFakeData(runInProgress: Boolean = false, errorMessage: String = "") {
        _runInProgress.value = runInProgress
        _errorMessage.value = errorMessage
        _dataList.value = listOf(
            PhotographersDTO(
                id = 1,
                stageName = "Bob la Menace",
                photoUrl = "https://www.amonteiro.fr/img/fakedata/bob.jpg",
                story = "Ancien agent secret, Bob a troqué ses gadgets pour un appareil photo après une mission qui a mal tourné. Il traque désormais les instants volés plutôt que les espions.",
                portfolio = listOf(
                    "https://example.com/photo1.jpg",
                    "https://example.com/photo2.jpg",
                    "https://example.com/photo3.jpg"
                )
            ),
            PhotographersDTO(
                id = 2,
                stageName = "Jean-Claude Flash",
                photoUrl = "https://www.amonteiro.fr/img/fakedata.com/jc.jpg",
                story = "Ancien champion de rodéo, il s’est reconverti en photographe après une chute mémorable. Maintenant, il dompte la lumière comme un vrai cow-boy.",
                portfolio = listOf(
                    "https://picsum.photos/407",
                    "https://picsum.photos/125",
                    "https://picsum.photos/549"
                )
            )
        )
    }
}
