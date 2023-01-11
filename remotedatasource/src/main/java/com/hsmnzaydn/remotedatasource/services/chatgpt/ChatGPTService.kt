package com.hsmnzaydn.remotedatasource.services.chatgpt

import com.hsmnzaydn.remotedatasource.model.ErrorResponse
import com.hsmnzaydn.remotedatasource.model.NetworkResponse
import com.hsmnzaydn.remotedatasource.services.chatgpt.request.AskQuestionRequest
import com.hsmnzaydn.remotedatasource.services.chatgpt.response.AskQuestionResponse
import retrofit2.http.Body
import retrofit2.http.POST

typealias GenericResponse<S> = NetworkResponse<S, ErrorResponse>

interface ChatGPTService {
    @POST("completions")
    suspend fun askQuestion(@Body askQuestionRequest: AskQuestionRequest) : GenericResponse<AskQuestionResponse>
}