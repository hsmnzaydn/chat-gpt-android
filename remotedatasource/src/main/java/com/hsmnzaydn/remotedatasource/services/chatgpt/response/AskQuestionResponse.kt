package com.hsmnzaydn.remotedatasource.services.chatgpt.response


import com.squareup.moshi.Json

data class AskQuestionResponse(
    @field:Json(name = "choices")
    var choices: List<Choice>,
    @field:Json(name = "created")
    var created: Int,
    @field:Json(name = "id")
    var id: String,
    @field:Json(name = "model")
    var model: String,
    @field:Json(name = "object")
    var objectX: String,
    @field:Json(name = "usage")
    var usage: Usage
)