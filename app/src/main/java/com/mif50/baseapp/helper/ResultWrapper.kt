package com.mif50.baseapp.helper

import com.mif50.baseapp.data.network.ModelResponse
import javax.inject.Named

typealias SimpleResult<T> = ResultWrapper<T, Throwable>

sealed class ResultWrapper<out T, out E> {

    data class Success<out T>(val value: T) : ResultWrapper<T, Nothing>()
    data class Failure<out E>(val error: E) : ResultWrapper<Nothing, E>()

    inline fun <C> fold(
        success: (T) -> C,
        failure: (E) -> C
    ): C = when (this) {
        is Success -> { success(value) }
        is Failure -> { failure(error) }
    }
}

@Named("mapper")
fun <T : ModelResponse> T.mapIntoResult(): SimpleResult<T> {
    return if (_response == null || response == 200 || response == 201 ||response == 202) {
        ResultWrapper.Success(value = this)
    } else {
        ResultWrapper.Failure(error = Throwable(this.message))
    }
}

