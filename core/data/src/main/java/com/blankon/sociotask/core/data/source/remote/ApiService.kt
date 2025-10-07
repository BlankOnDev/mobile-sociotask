package com.blankon.sociotask.core.data.source.remote

import com.blankon.sociotask.core.data.model.response.SampleModelResponse
import com.blankon.sociotask.core.domain.auth.model.User
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("codingresources/codingResources")
    suspend fun getListData(): List<SampleModelResponse>

    @POST("register")
    suspend fun signUpWithEmail(): User

    @POST("login")
    suspend fun signInWithEmail(): User
}