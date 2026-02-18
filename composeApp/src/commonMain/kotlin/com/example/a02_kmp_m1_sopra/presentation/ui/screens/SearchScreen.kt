package com.example.a02_kmp_m1_sopra.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a02_kmp_m1_sopra.presentation.ui.theme.AppTheme
import com.example.a02_kmp_m1_sopra.presentation.viewmodel.MainViewModel


@Preview(showBackground = true, showSystemUi = true)
@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = 32
)
@Composable
fun SearchScreenEmptyPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SearchScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenFullPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

            val mainViewModel = MainViewModel()
            mainViewModel.loadFakeData()

            SearchScreen(modifier = Modifier.padding(innerPadding), mainViewModel = mainViewModel)
        }
    }
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel() { MainViewModel() }) {
    Column(modifier = modifier) {
        println("SearchScreen()")

        mainViewModel.dataList.collectAsStateWithLifecycle().value.forEach {
            PictureRowItem(text = it.stageName)
        }

    }
}

@Composable
fun PictureRowItem(text: String) {
    Text(text = text, fontSize = 14.sp)
}