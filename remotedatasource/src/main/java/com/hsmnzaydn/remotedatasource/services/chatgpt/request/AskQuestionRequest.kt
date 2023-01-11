package com.hsmnzaydn.remotedatasource.services.chatgpt.request


import com.squareup.moshi.Json

data class AskQuestionRequest(
    @field:Json(name = "max_tokens")
    var maxTokens: Int,
    @field:Json(name = "model")
    var model: String,
    @field:Json(name = "prompt")
    var prompt: String,
    @field:Json(name = "temperature")
    var temperature: Int
)