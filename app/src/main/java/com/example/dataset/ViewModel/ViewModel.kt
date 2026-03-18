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

    // LiveData de resultado para operaciones POST (addPokemon, addFavorite)
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

    fun filterByType(type: String) {
        viewModelScope.launch {
            try {
                _error.postValue(null)
                val response = repository.getByType(type)
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

    fun selectPokemon(num: Int) {
        _selectedPokemon.value = _pokemonList.value?.find { it.Num == num }
    }

    fun addPokemon(pokemon: Map<String, Any>) {
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

    fun addFavorite(name: String, type1: String, num: Int) {
        viewModelScope.launch {
            try {
                _error.postValue(null)
                val response = repository.addFavorite(name, type1, num)
                _result.postValue(response)
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    fun clearResult() {
        _result.value = null
    }
}