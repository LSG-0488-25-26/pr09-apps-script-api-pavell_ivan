package com.example.dataset.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dataset.model.ApiResponse
import com.example.dataset.model.Pokemon
import com.example.dataset.model.PokemonRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repository = PokemonRepository()

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    // LiveData de resultado para operaciones POST
    private val _result = MutableLiveData<ApiResponse<Nothing>?>()
    val result: LiveData<ApiResponse<Nothing>?> = _result

    // Pokémon seleccionado para la pantalla de detalle
    private val _selectedPokemon = MutableLiveData<Pokemon?>()
    val selectedPokemon: LiveData<Pokemon?> = _selectedPokemon

    fun loadAll() {
        viewModelScope.launch {
            try {
                _error.postValue(null)
                val response = repository.getAllPokemon()
                _pokemonList.postValue(response.data ?: emptyList())
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun searchByName(name: String) {
        viewModelScope.launch {
            try {
                _error.postValue(null)
                val response = repository.getByName(name)
                _pokemonList.postValue(response.data ?: emptyList())
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun addPokemon(pokemon: Map<String, @JvmSuppressWildcards Any>) {
        viewModelScope.launch {
            try {
                _error.postValue(null)
                val response = repository.addPokemon(pokemon)
                _result.postValue(response)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
    fun clearResult() {
        _result.value = null
    }

    fun isDuplicate(name: String, num: Int): Boolean {
        return _pokemonList.value?.any { pokemon ->
            pokemon.Name.equals(name.trim(), ignoreCase = true) || pokemon.Num == num
        } ?: false
    }
}