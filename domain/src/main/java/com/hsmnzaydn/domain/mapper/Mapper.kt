package com.hsmnzaydn.domain.mapper

interface Mapper<in T,out C> {
    fun map(response: T) : C
}