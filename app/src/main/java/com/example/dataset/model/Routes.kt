package com.example.dataset.model


sealed class Routes(val route: String) {
    object Login    : Routes("login")
    object Register : Routes("register")
    object List     : Routes("lista")
    object AddPokemon : Routes("add_pokemon")
    object Detail   : Routes("detail/{num}") {
        fun createRoute(num: Int) = "detail/$num"
    }
}