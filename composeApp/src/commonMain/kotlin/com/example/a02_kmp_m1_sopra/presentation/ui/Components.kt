package com.example.a02_kmp_m1_sopra.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun PictureGallery(modifier: Modifier = Modifier, urlList: List<String>)