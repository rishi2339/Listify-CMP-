package com.cmp.todo.util

sealed class BaseResult<out T : Any, out U : Any> {
    data class Success<T : Any>(val data: T) : BaseResult<T, Nothing>()
    data class Error<U : Any>(val rawResponse: U) : BaseResult<Nothing, U>()
}
sealed class Result<out T : Any> {
    data class Success<T : Any>(val data: T) : Result<T>()
    data class Error<T : Any>(val rawResponse: T) : Result<T>()
}
