package com.cmp.todo.util

sealed class Resource<T>(val data:T?=null, val message:String?=null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data,message)
    class Loading<T>(val isLoading:Boolean = true): Resource<T>(data = null)
}
sealed interface ResourceUiState<out T> {
    data class Success<T>(val data: T) : ResourceUiState<T>
    data class Error(val message: String? = null) : ResourceUiState<Nothing>
    object Loading : ResourceUiState<Nothing>
    object Empty : ResourceUiState<Nothing>
    object Idle : ResourceUiState<Nothing>
}