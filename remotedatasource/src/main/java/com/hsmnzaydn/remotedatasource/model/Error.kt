package com.hsmnzaydn.remotedatasource.model


import com.squareup.moshi.Json

data class Error(
    @field:Json(name = "code")
    var code: Any,
    @field:Json(name = "message")
    var message: String,
    @field:Json(name = "param")
    var `param`: Any,
    @field:Json(name = "type")
    var type: String
)