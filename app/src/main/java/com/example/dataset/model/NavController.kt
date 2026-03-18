package com.example.dataset.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dataset.ViewModel.MainViewModel
import com.example.dataset.view.AddPokemonScreen
import com.example.dataset.view.Login
import com.example.dataset.view.PokemonDetailScreen
import com.example.dataset.view.PokemonListScreen
import com.example.dataset.view.RegisterScreen

@Composable
fun AppNavHost(viewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()
    val context       = LocalContext.current

    // Determina la pantalla inicial según si ya hay sesión
    val start = if (SharedPrefsManager.isLoggedIn(context))
        Routes.List.route else Routes.Login.route

    NavHost(navController = navController, startDestination = start) {

        composable(Routes.Login.route) {
            Login(
                onLoginSuccess = {
                    navController.navigate(Routes.List.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                },
                onGoRegister = {
                    navController.navigate(Routes.Register.route)
                }
            )
        }

        composable(Routes.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Routes.List.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.List.route) {
            PokemonListScreen(
                viewModel     = viewModel,
                onAddClick    = { navController.navigate(Routes.AddPokemon.route) },
                onDetailClick = { num ->
                    navController.navigate(Routes.Detail.createRoute(num))
                },
                onLogout      = {
                    SharedPrefsManager.logout(context)
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.List.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.AddPokemon.route) {
            AddPokemonScreen(
                viewModel = viewModel,
                onBack    = { navController.popBackStack() }
            )
        }

        composable(Routes.Detail.route) { backStackEntry ->
            val num = backStackEntry.arguments?.getString("num")?.toIntOrNull()
            PokemonDetailScreen(
                num       = num,
                viewModel = viewModel,
                onBack    = { navController.popBackStack() }
            )
        }
    }
}