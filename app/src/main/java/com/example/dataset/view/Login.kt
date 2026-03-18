package com.example.dataset.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dataset.model.SharedPrefsManager

@Composable
fun Login(
    onLoginSuccess: () -> Unit,
    onGoRegister:   () -> Unit
) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error    by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Pokémon App", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value         = username,
            onValueChange = { username = it },
            label         = { Text("Usuari") },
            modifier      = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value                = password,
            onValueChange        = { password = it },
            label                = { Text("Contrasenya") },
            visualTransformation = PasswordVisualTransformation(),
            modifier             = Modifier.fillMaxWidth()
        )

        if (error.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(error, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                val saved = SharedPrefsManager.getCredentials(context)
                if (saved != null &&
                    saved.first == username &&
                    saved.second == password
                ) {
                    SharedPrefsManager.setLoggedIn(context, true)
                    onLoginSuccess()
                } else {
                    error = "Usuari o contrasenya incorrectes"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Entrar") }

        Spacer(Modifier.height(12.dp))
        OutlinedButton(
            onClick  = onGoRegister,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Crear compte") }
    }
}