package com.example.dataset.view

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dataset.ViewModel.MainViewModel
import com.example.dataset.model.PokemonType

// Tipus disponibles per al Tipus 1 (sense NONE)
private val TYPE1_OPTIONS = PokemonType.entries.filter { it != PokemonType.NONE }

// Tipus disponibles per al Tipus 2 (NONE primer, com a "cap tipus")
private val TYPE2_OPTIONS = listOf(PokemonType.NONE) + TYPE1_OPTIONS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPokemonScreen(
    viewModel: MainViewModel = viewModel(),
    onBack: () -> Unit
) {
    val result   by viewModel.result.observeAsState()
    val pokemons by viewModel.pokemonList.observeAsState(emptyList())
    val error    by viewModel.error.observeAsState()

    // Num automàtic: màxim actual + 1
    val nextNum = remember(pokemons) {
        (pokemons.maxOfOrNull { it.Num } ?: 0) + 1
    }

    var name      by remember { mutableStateOf("") }
    var type1     by remember { mutableStateOf<PokemonType?>(null) }
    var type2     by remember { mutableStateOf(PokemonType.NONE) }
    var hp        by remember { mutableFloatStateOf(45f) }
    var attack    by remember { mutableFloatStateOf(50f) }
    var defense   by remember { mutableFloatStateOf(50f) }
    var spAtk     by remember { mutableFloatStateOf(50f) }
    var spDef     by remember { mutableFloatStateOf(50f) }
    var speed     by remember { mutableFloatStateOf(50f) }
    var legendary by remember { mutableStateOf(false) }

    var nameError  by remember { mutableStateOf(false) }
    var type1Error by remember { mutableStateOf(false) }

    LaunchedEffect(result) {
        if (result?.status == "ok") {
            viewModel.clearResult()
            onBack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title          = { Text("Nou Pokémon") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tornar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // ── Num automàtic ──────────────────────────────────────────
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Número assignat automàticament:",
                    fontSize = 13.sp,
                    color    = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    "#$nextNum",
                    fontWeight = FontWeight.Bold,
                    color      = MaterialTheme.colorScheme.primary,
                    fontSize   = 16.sp
                )
            }

            HorizontalDivider()

            // ── Nom ────────────────────────────────────────────────────
            SectionTitle("Informació bàsica")

            OutlinedTextField(
                value          = name,
                onValueChange  = { name = it; nameError = false },
                label          = { Text("Nom *") },
                isError        = nameError,
                supportingText = if (nameError) ({ Text("El nom és obligatori") }) else null,
                modifier       = Modifier.fillMaxWidth()
            )

            // ── Tipus ──────────────────────────────────────────────────
            SectionTitle("Tipus")

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Tipus 1 — obligatori, sense NONE
                TypeDropdown(
                    label       = "Tipus 1 *",
                    selected    = type1,
                    options     = TYPE1_OPTIONS,
                    isError     = type1Error,
                    placeholder = "Selecciona...",
                    onSelect    = { type1 = it; type1Error = false },
                    modifier    = Modifier.weight(1f)
                )
                // Tipus 2 — opcional, NONE = cap tipus
                TypeDropdown(
                    label       = "Tipus 2",
                    selected    = type2,
                    options     = TYPE2_OPTIONS,
                    isError     = false,
                    placeholder = PokemonType.NONE.displayName,
                    onSelect    = { type2 = it },
                    modifier    = Modifier.weight(1f)
                )
            }
            if (type1Error) {
                Text(
                    "Cal seleccionar almenys el Tipus 1",
                    color    = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp
                )
            }

            HorizontalDivider()

            // ── Estadístiques (1–255) ──────────────────────────────────
            SectionTitle("Estadístiques  (1 – 255)")

            StatSlider("HP",      hp,      { hp = it })
            StatSlider("Attack",  attack,  { attack = it })
            StatSlider("Defense", defense, { defense = it })
            StatSlider("Sp. Atk", spAtk,  { spAtk = it })
            StatSlider("Sp. Def", spDef,  { spDef = it })
            StatSlider("Speed",   speed,  { speed = it })

            HorizontalDivider()

            // ── Llegendari ─────────────────────────────────────────────
            SectionTitle("Atributs especials")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        width = 1.dp,
                        color = if (legendary) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable { legendary = !legendary }
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Llegendari", fontWeight = FontWeight.Medium)
                    Text(
                        "Marqueu si és un Pokémon llegendari",
                        fontSize = 12.sp,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(checked = legendary, onCheckedChange = { legendary = it })
            }

            // ── Error de xarxa ─────────────────────────────────────────
            error?.let {
                Text("Error: $it", color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
            }

            // ── Botó guardar ───────────────────────────────────────────
            Button(
                onClick = {
                    nameError  = name.isBlank()
                    type1Error = type1 == null
                    if (nameError || type1Error) return@Button

                    viewModel.addPokemon(
                        mapOf(
                            "Num"        to nextNum,
                            "Name"       to name.trim(),
                            "Type1"      to (type1!!.displayName),
                            // Si type2 és NONE enviem string buit (la sheet accepta cel·la buida)
                            "Type2"      to if (type2 == PokemonType.NONE) "" else type2.displayName,
                            "HP"         to hp.toInt(),
                            "Attack"     to attack.toInt(),
                            "Defense"    to defense.toInt(),
                            "SpAtk"      to spAtk.toInt(),
                            "SpDef"      to spDef.toInt(),
                            "Speed"      to speed.toInt(),
                            "Generation" to 11,
                            "Legendary"  to if (legendary) "TRUE" else "FALSE"
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Guardar Pokémon", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}