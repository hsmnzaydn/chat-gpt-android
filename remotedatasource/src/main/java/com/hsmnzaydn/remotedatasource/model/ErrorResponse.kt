package com.hsmnzaydn.remotedatasource.model

import com.squareup.moshi.Json


data class ErrorResponse(
    @field:Json(name = "error")
    var error: Error
)