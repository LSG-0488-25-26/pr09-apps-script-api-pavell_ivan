package com.example.dataset.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.dataset.model.PokemonType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypeDropdown(
    label: String,
    selected: PokemonType?,
    options: List<PokemonType>,
    isError: Boolean,
    placeholder: String,
    onSelect: (PokemonType) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val displayText = selected?.displayName ?: placeholder

    ExposedDropdownMenuBox(
        expanded         = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier         = modifier
    ) {
        OutlinedTextField(
            value         = displayText,
            onValueChange = {},
            readOnly      = true,
            label         = { Text(label) },
            isError       = isError,
            trailingIcon  = {
                Icon(
                    if (expanded) Icons.Default.KeyboardArrowUp
                    else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded         = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { pokemonType ->
                DropdownMenuItem(
                    text    = { Text(pokemonType.displayName) },
                    onClick = {
                        onSelect(pokemonType)
                        expanded = false
                    },
                    leadingIcon = if (pokemonType != PokemonType.NONE) ({
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(pokemonType.toColor())
                        )
                    }) else null
                )
            }
        }
    }
}

// Extensió sobre l'enum per obtenir el color associat al tipus
private fun PokemonType.toColor(): Color = when (this) {
    PokemonType.FIRE     -> Color(0xFFFF9741)
    PokemonType.WATER    -> Color(0xFF3692DC)
    PokemonType.GRASS    -> Color(0xFF38BF4B)
    PokemonType.ELECTRIC -> Color(0xFFFBD100)
    PokemonType.ICE      -> Color(0xFF70CBD4)
    PokemonType.FIGHTING -> Color(0xFFCE416B)
    PokemonType.POISON   -> Color(0xFFB567CE)
    PokemonType.GROUND   -> Color(0xFFCC9741)
    PokemonType.FLYING   -> Color(0xFF89AAE3)
    PokemonType.PSYCHIC  -> Color(0xFFFF6675)
    PokemonType.BUG      -> Color(0xFF91C12F)
    PokemonType.ROCK     -> Color(0xFFC5B78C)
    PokemonType.GHOST    -> Color(0xFF5269AD)
    PokemonType.DRAGON   -> Color(0xFF0F6AC0)
    PokemonType.DARK     -> Color(0xFF5B5466)
    PokemonType.STEEL    -> Color(0xFF5A8EA2)
    PokemonType.FAIRY    -> Color(0xFFEC8FE6)
    PokemonType.NORMAL,
    PokemonType.NONE     -> Color(0xFFA8A878)
}