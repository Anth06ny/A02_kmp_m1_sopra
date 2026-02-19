package com.example.a02_kmp_m1_sopra

import a02_kmp_m1_sopra.composeapp.generated.resources.Res
import a02_kmp_m1_sopra.composeapp.generated.resources.compose_multiplatform
import a02_kmp_m1_sopra.composeapp.generated.resources.error
import a02_kmp_m1_sopra.composeapp.generated.resources.my_key
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.a02_kmp_m1_sopra.data.remote.MuseumDTO
import com.example.a02_kmp_m1_sopra.presentation.ui.screens.SearchScreen
import com.example.a02_kmp_m1_sopra.presentation.ui.theme.AppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun App() {
    AppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            SearchScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}



