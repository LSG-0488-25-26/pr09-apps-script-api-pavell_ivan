package com.example.dataset.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dataset.ViewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    num:       Int?,
    viewModel: MainViewModel = viewModel(),
    onBack:    () -> Unit
) {
    val pokemons by viewModel.pokemonList.observeAsState(emptyList())
    val result   by viewModel.result.observeAsState()
    val snackbar  = remember { SnackbarHostState() }

    val pokemon = pokemons.find { it.Num == num }

    LaunchedEffect(result) {
        if (result?.status == "ok") {
            snackbar.showSnackbar("Afegit als favorits!")
            viewModel.clearResult()
        }
    }

    Scaffold(
        snackbarHost   = { SnackbarHost(snackbar) },
        topBar = {
            TopAppBar(
                title          = { Text(pokemon?.Name ?: "Detall") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tornar")
                    }
                }
            )
        }
    ) { padding ->
        if (pokemon == null) {
            Text(
                "Pokémon no trobat",
                modifier = Modifier.padding(padding).padding(16.dp)
            )
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "#${pokemon.Num} ${pokemon.Name}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Tipus: ${pokemon.Type1}${if (!pokemon.Type2.isNullOrBlank()) " / ${pokemon.Type2}" else ""}",
                fontSize = 14.sp,
                color    = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (pokemon.Legendary == "TRUE") {
                Text("⭐ Llegendari", color = MaterialTheme.colorScheme.primary)
            }

            Spacer(Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors   = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    StatRow("HP",         pokemon.HP)
                    StatRow("Attack",     pokemon.Attack)
                    StatRow("Defense",    pokemon.Defense)
                    StatRow("Sp. Atk",    pokemon.SpAtk)
                    StatRow("Sp. Def",    pokemon.SpDef)
                    StatRow("Speed",      pokemon.Speed)
                    StatRow("Generació",  pokemon.Generation)
                }
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = FontWeight.Medium)
        Text(value.toString())
    }
}