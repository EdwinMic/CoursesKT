package com.example.coursekt.ui.states

import com.example.coursekt.domain.PersonDataResponse
import retrofit2.Response

sealed class DragonUIState {
    object Loading: DragonUIState()
    data class Success(val response: Response<PersonDataResponse>): DragonUIState()
    data class Error(val message: String): DragonUIState()
}