package com.example.coursekt.ui.states

sealed class MainUIState {

    object Loading: MainUIState()
    data class Success(val likes: Int): MainUIState()
    data class Error(val message: String): MainUIState()
}