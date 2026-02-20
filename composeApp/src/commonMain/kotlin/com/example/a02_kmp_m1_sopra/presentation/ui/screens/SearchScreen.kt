package com.example.a02_kmp_m1_sopra.presentation.ui.screens

import a02_kmp_m1_sopra.composeapp.generated.resources.Res
import a02_kmp_m1_sopra.composeapp.generated.resources.error
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.a02_kmp_m1_sopra.data.remote.PhotographersDTO
import com.example.a02_kmp_m1_sopra.di.apiModule
import com.example.a02_kmp_m1_sopra.di.viewModelModule
import com.example.a02_kmp_m1_sopra.presentation.ui.theme.AppTheme
import com.example.a02_kmp_m1_sopra.presentation.viewmodel.MainViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.KoinApplicationPreview
import org.koin.compose.viewmodel.koinViewModel


@Preview(showBackground = true, showSystemUi = true)
@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = 32
)
@Composable
fun SearchScreenEmptyPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...} val context = LocalContext.current
    KoinApplicationPreview(application = {
        modules(viewModelModule, apiModule)
    }) {
        AppTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val mainViewModel: MainViewModel = koinViewModel()
                SearchScreen(modifier = Modifier.padding(innerPadding), mainViewModel = mainViewModel)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenFullPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    KoinApplicationPreview(application = {
        modules(viewModelModule, apiModule)
    }) {
        AppTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                val mainViewModel: MainViewModel = koinViewModel()
                mainViewModel.loadFakeData()
                SearchScreen(modifier = Modifier.padding(innerPadding), mainViewModel = mainViewModel)
            }
        }
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    onPictureClick: (PhotographersDTO) -> Unit = {}
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        println("SearchScreen()")

        var searchText by remember { mutableStateOf("") }

        SearchBar(text = searchText) {
            searchText = it
        }

        val list = mainViewModel.dataList.collectAsStateWithLifecycle().value.filter {
            it.stageName.contains(searchText, true)
        }
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)

        ) {
            items(list.size) {
                PictureRowItem(data = list[it], onPictureClick = onPictureClick)
            }
        }

        Row {
            Button(
                onClick = { searchText = "" },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Clear filter")
            }

            Button(
                onClick = { mainViewModel.loadPhotographers() },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text("Load data")
            }
        }

    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, text: String, onValueChange: (String) -> Unit) {


    TextField(
        value = text, //Valeur affichée
        onValueChange = onValueChange, //Nouveau texte entrée
        leadingIcon = { //Image d'icône
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        },
        singleLine = true,
        label = { //Texte d'aide qui se déplace
            Text("Enter text")
            //Pour aller le chercher dans string.xml, R de votre package com.nom.projet
            //Text(stringResource(R.string.placeholder_search))
        },
        //placeholder = { //Texte d'aide qui disparait
        //Text("Recherche")
        //},

        //keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search), // Définir le bouton "Entrée" comme action de recherche
        //keyboardActions = KeyboardActions(onSearch = {onSearchAction()}), // Déclenche l'action définie
        //Comment le composant doit se placer
        modifier = modifier
            .fillMaxWidth() // Prend toute la largeur
            .heightIn(min = 56.dp) //Hauteur minimum
    )
}

@Composable //Composable affichant 1 élément
fun PictureRowItem(
    modifier: Modifier = Modifier, data: PhotographersDTO,
    onPictureClick: (PhotographersDTO) -> Unit
) {

    var expended by remember { mutableStateOf(false) }


    Row(
        modifier = modifier
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
                .clickable {
                    onPictureClick(data)
                }
        )

        Column(
            modifier = Modifier.clickable { expended = !expended }.fillMaxWidth()

        ) {
            Text(data.stageName, fontSize = 20.sp)
            val text = if (expended) data.story else (data.story.take(20) + "...")
            Text(text, fontSize = 14.sp, modifier = Modifier.animateContentSize())
        }

    }
}