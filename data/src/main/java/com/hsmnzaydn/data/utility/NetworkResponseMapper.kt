package com.hsmnzaydn.data.utility

import com.hsmnzaydn.domain.mapper.Mapper
import com.hsmnzaydn.remotedatasource.model.NetworkResponse
import com.hsmnzaydn.remotedatasource.model.ErrorResponse
import com.hsmnzaydn.domain.chatgpt.DomainState

fun <T : Any, C : Any> NetworkResponse<T, ErrorResponse>.toDomainState(mapper: Mapper<T, C>): DomainState<C> {
    return when (this) {
        is NetworkResponse.Success -> DomainState.Success(mapper.map(this.body))
        is NetworkResponse.ApiError -> DomainState.ErrorState(this.body.error.message)
        is NetworkResponse.NetworkError -> DomainState.ErrorState(this.error.message.toString())
        is NetworkResponse.UnknownError -> DomainState.ErrorState(this.error.message.toString())
    }
}