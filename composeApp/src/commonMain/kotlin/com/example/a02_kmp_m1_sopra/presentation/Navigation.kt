package com.example.a02_kmp_m1_sopra.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.a02_kmp_m1_sopra.presentation.ui.screens.PhotographerScreen
import com.example.a02_kmp_m1_sopra.presentation.ui.screens.SearchScreen
import com.example.a02_kmp_m1_sopra.presentation.viewmodel.MainViewModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

class Routes {
    @Serializable
    data object SearchRoute

    //les paramètres ne peuvent être que des types de base(String, Int, Double...)
    @Serializable
    data class PhotographerDetailRoute(val id: Int)
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navHostController : NavHostController = rememberNavController()
    //viewModel() en dehors de NavHost lie à l'Activité donc partagé entre les écrans
    //viewModel() dans le NavHost lié à la stack d'écran. Une instance par stack d'écran
    val mainViewModel  = koinViewModel<MainViewModel>()

    //Import version avec Composable
    NavHost(
        navController = navHostController,
        startDestination = Routes.SearchRoute,
        modifier = modifier) {
        //Route 1 vers notre SearchScreen
        composable<Routes.SearchRoute> {

            //Si créé ici, il sera propre à cet instance de l'écran
            //val mainViewModel : MainViewModel = viewModel()

            //on peut passer le navHostController à un écran s'il déclenche des navigations
            SearchScreen(mainViewModel = mainViewModel, onPictureClick = {
                navHostController.navigate(Routes.PhotographerDetailRoute(it.id))
            } )
        }

        //Route 2 vers un écran de détail
        composable<Routes.PhotographerDetailRoute> {
            val detailRoute = it.toRoute<Routes.PhotographerDetailRoute>()
            val photographersDTO = mainViewModel.dataList.collectAsStateWithLifecycle().value.first { it.id == detailRoute.id }

            PhotographerScreen(photographer = photographersDTO)

        }
    }
}