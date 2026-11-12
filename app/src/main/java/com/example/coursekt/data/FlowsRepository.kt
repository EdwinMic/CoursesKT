package com.example.coursekt.data

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.milliseconds

class FlowsRepository {

    val counter: Flow<Int> = flow {
        var likes = 0
        while (true) {
            emit(likes++)
            delay(1000.milliseconds)
        }
    }
}