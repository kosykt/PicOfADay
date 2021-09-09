package ru.konstantin.material.model

sealed class ViewState {
    data class Success<out T>(val stateData: T): ViewState()
    class Error(val error: Throwable): ViewState()
    object Loading: ViewState()
}