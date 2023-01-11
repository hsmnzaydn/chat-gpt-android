package com.hsmnzaydn.remotedatasource.services.chatgpt.response

import com.squareup.moshi.Json


data class Choice(
    @field:Json(name = "finish_reason")
    var finishReason: String,
    @field:Json(name = "index")
    var index: Int,
    @field:Json(name = "logprobs")
    var logprobs: Any,
    @field:Json(name = "text")
    var text: String
)