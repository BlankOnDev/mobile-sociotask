package com.blankon.sociotask.core.data.source.remote

import com.blankon.sociotask.core.data.model.request.CreateTaskRequest
import com.blankon.sociotask.core.data.model.request.SignInRequest
import com.blankon.sociotask.core.data.model.request.SignUpRequest
import com.blankon.sociotask.core.data.model.response.ApiResponse
import com.blankon.sociotask.core.data.model.response.ListTask
import com.blankon.sociotask.core.data.model.response.SampleModelResponse
import com.blankon.sociotask.core.data.model.response.SignInPayload
import com.blankon.sociotask.core.data.model.response.SignUpPayload
import com.blankon.sociotask.core.domain.model.Task
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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

    //    With Auth
    @POST("tasks")
    suspend fun createTask(
        @Body body: CreateTaskRequest
    ): ApiResponse<Task>


    //    No Auth
    @GET("tasks")
    suspend fun getAllTasks(
        @Query("page") page: Int
    ): ApiResponse<ListTask>
}