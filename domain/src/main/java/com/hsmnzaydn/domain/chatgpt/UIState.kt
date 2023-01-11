package com.hsmnzaydn.domain.chatgpt

sealed class UIState<T> {
    data class Success<T>(
        val body: T,
    ) : UIState<T>()

    data class ErrorState<T>(
        val message: String,
    ) : UIState<T>()
}
