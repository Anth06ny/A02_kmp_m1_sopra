package com.example.a02_kmp_m1_sopra.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun SearchScreenPreview() {
    //Il faut remplacer NomVotreAppliTheme par le thème de votre application
    //Utilisé par exemple dans MainActivity.kt sous setContent {...}
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SearchScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier, mainViewModel: MainViewModel = viewModel() { MainViewModel() }) {
    Column (modifier= modifier) {
        println("SearchScreen()")
        Text(text = "Text1",fontSize = 20.sp,
modifier = Modifier.padding(horizontal = 8.dp)
            )
        Spacer(Modifier.size(8.dp))
        Text(text = "Text2",fontSize = 14.sp)

    }
}

@Composable
fun PictureRowItem(text:String){
    Text(text = text,fontSize = 14.sp)
}