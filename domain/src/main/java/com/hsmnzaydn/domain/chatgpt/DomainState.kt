package com.hsmnzaydn.domain.chatgpt

sealed class DomainState<T> {
    data class Success<T>(
        val body: T,
    ) : DomainState<T>()

    data class ErrorState<T>(
        val message: String,
    ) : DomainState<T>()
}
