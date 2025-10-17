package com.blankon.sociotask.core.data.source.remote

import com.blankon.sociotask.core.data.model.request.SignInRequest
import com.blankon.sociotask.core.data.model.request.SignUpRequest
import com.blankon.sociotask.core.data.model.response.ApiResponse
import com.blankon.sociotask.core.data.model.response.SampleModelResponse
import com.blankon.sociotask.core.data.model.response.SignInPayload
import com.blankon.sociotask.core.data.model.response.SignUpPayload
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("codingresources/codingResources")
    suspend fun getListData(): List<SampleModelResponse>

    @POST("register")
    suspend fun signUpWithEmail(
        @Body signUpRequest: SignUpRequest
    ): ApiResponse<SignUpPayload>

    @POST("login")
    suspend fun signInWithEmail(
        @Body signInPayload: SignInRequest
    ): ApiResponse<SignInPayload>
}