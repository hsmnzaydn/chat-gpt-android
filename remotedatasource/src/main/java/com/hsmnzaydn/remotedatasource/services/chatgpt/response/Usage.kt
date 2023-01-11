package com.hsmnzaydn.remotedatasource.services.chatgpt.response

import com.squareup.moshi.Json


data class Usage(
    @field:Json(name = "completion_tokens")
    var completionTokens: Int,
    @field:Json(name = "prompt_tokens")
    var promptTokens: Int,
    @field:Json(name = "total_tokens")
    var totalTokens: Int
)