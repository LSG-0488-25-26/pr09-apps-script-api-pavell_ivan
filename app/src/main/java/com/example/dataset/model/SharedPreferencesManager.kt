package com.example.dataset.model

import android.content.Context

object SharedPrefsManager {
    private const val PREFS_NAME = "pokemon_prefs"
    private const val KEY_USER   = "username"
    private const val KEY_PASS   = "password"
    private const val KEY_LOGGED = "is_logged"

    fun saveCredentials(context: Context, username: String, password: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit().apply {
            putString(KEY_USER, username)
            putString(KEY_PASS, password)
            apply()
        }
    }

    /** Retorna el parell (username, password) si existeix, o null */
    fun getCredentials(context: Context): Pair<String, String>? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val user  = prefs.getString(KEY_USER, null)
        val pass  = prefs.getString(KEY_PASS, null)
        return if (user != null && pass != null) Pair(user, pass) else null
    }

    fun setLoggedIn(context: Context, value: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .putBoolean(KEY_LOGGED, value).apply()
    }

    fun isLoggedIn(context: Context): Boolean =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_LOGGED, false)

    fun logout(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
            .putBoolean(KEY_LOGGED, false).apply()
        // Nota: no borrem les credencials per permetre re-login sense re-registre
    }

    fun getUsername(context: Context): String? =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_USER, null)
}