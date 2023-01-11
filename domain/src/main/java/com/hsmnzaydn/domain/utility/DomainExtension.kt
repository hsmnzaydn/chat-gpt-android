package com.hsmnzaydn.domain.utility

import com.hsmnzaydn.domain.chatgpt.DomainState
import com.hsmnzaydn.domain.chatgpt.UIState
import com.hsmnzaydn.domain.mapper.Mapper
import kotlinx.coroutines.flow.FlowCollector

fun <T, K> DomainState<T>.getUIState(
    mapper: Mapper<T, K>,
): UIState<K> {
    return when (this) {
        is DomainState.Success -> UIState.Success(body = mapper.map(this.body))
        is DomainState.ErrorState -> UIState.ErrorState(message = this.message)
    }
}