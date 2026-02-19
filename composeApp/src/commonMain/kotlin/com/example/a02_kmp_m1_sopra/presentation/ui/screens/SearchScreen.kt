package com.example.a02_kmp_m1_sopra.presentation.ui.screens

import a02_kmp_m1_sopra.composeapp.generated.resources.Res
import a02_kmp_m1_sopra.composeapp.generated.resources.error
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.a02_kmp_m1_sopra.data.remote.PhotographersDTO
import com.example.a02_kmp_m1_sopra.presentation.ui.theme.AppTheme
import com.example.a02_kmp_m1_sopra.presentation.viewmodel.MainViewModel
import org.jetbrains.compose.resources.painterResource


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
            PictureRowItem(data = it)
        }

    }
}

@Composable //Composable affichant 1 élément
fun PictureRowItem(modifier: Modifier = Modifier, data: PhotographersDTO) {

    Row(modifier = modifier
        .background(MaterialTheme.colorScheme.tertiary)
        .padding(4.dp)
        .background(MaterialTheme.colorScheme.primary)
        .padding(8.dp)
        .fillMaxWidth()
    ) {

        //Permission Internet nécessaire
        AsyncImage(
            model = data.photoUrl,
            //Pour aller le chercher dans string.xml R de votre package com.nom.projet
            //contentDescription = getString(R.string.picture_of_cat),
            //En dur
            contentDescription = "une photo de chat",
            contentScale = ContentScale.FillWidth,

            //Pour toto.png. Si besoin de choisir l'import pour la classe R, c'est celle de votre package
            //Image d'échec de chargement qui sera utilisé par la preview
            error = painterResource(Res.drawable.error),
            //Image d'attente.
            //placeholder = painterResource(R.drawable.toto),

            onError = { println(it) },
            modifier = Modifier
                .heightIn(max = 100.dp)
                .widthIn(max = 100.dp)
        )

        Column {
            Text(data.stageName, fontSize = 20.sp)
            Text(data.story.take(20) + "...", fontSize = 14.sp)
        }

    }
}