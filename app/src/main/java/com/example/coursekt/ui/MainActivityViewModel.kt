package com.example.coursekt.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursekt.data.FlowsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {
    val repository = FlowsRepository()

    /* Se debe de indicar el estado del stateFlow, en este caso es Loading */
    private val _uiState = MutableStateFlow<MainUIState>(MainUIState.Loading)
    /* Estado al que el usuario va a tener acceso sin poder actualizarlo, solo leerlo */
    val uiState: StateFlow<MainUIState> = _uiState.asStateFlow()

    fun example() {
        /* El repositorio colecta un flow del contador y se actualiza cada segundo */
        viewModelScope.launch {
            repository.counter
                .map{
                    /*La funcion map transforma los datos que regresa el flow*/
                    it.toString()
                }
                .collect { likes ->
                    Log.e("", "Likes:: $likes")
                }
        }
    }

    fun example2() {
        viewModelScope.launch {
            repository.counter
                .map{
                    /*La funcion map transforma los datos que regresa el flow*/
                    it.toString()
                }
                .onEach {
                    /*La funcion onEach se utiliza para realizar funciones secundarias sin modificar la informacion*/
                }
                .catch { error ->
                    /*La funcion catch se utiliza para manejar errores*/
                    Log.e("", "Likes:: ${error.message}")
                }
                .collect { likes ->
                    Log.e("", "Likes:: $likes")
                }
        }
    }

    fun example3() {
        viewModelScope.launch {
            repository.counter
                .map{
                    /*La funcion map transforma los datos que regresa el flow*/
                    it.toString()
                }
                .onEach {
                    /*La funcion onEach se utiliza para realizar funciones secundarias sin modificar la informacion*/
                }
                .catch { error ->
                    /*La funcion catch se utiliza para manejar errores*/
                    _uiState.value = MainUIState.Error(error.message.orEmpty())
                    Log.e("", "Likes:: ${error.message}")
                }
                // Se ejecuta en un hilo separado del hilo principal, el codigo antes de este metodo se ejecutan en un hilo seundario y el codigo despues de este metodo se ejecutan en el hilo principal
                .flowOn(Dispatchers.IO)
                .collect { likes ->
                    _uiState.value = MainUIState.Success(likes.toInt())
                    Log.e("", "Likes:: $likes")
                }
        }
    }
}