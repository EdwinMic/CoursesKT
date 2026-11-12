package com.example.coursekt.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coursekt.data.FlowsRepository
import com.example.coursekt.ui.states.MainUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
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
                .catch { error ->
                    /*La funcion catch se utiliza para manejar errores*/
                    _uiState.value = MainUIState.Error(error.message.orEmpty())
                    Log.e("", "Error example3:: ${error.message}")
                }
                .flowOn(Dispatchers.IO)
                .collect { likes ->
                    _uiState.value = MainUIState.Success(likes.toInt())
                    Log.e("", "Likes:: $likes")
                }
        }
    }
}