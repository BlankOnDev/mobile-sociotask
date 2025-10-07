package com.blankon.sociotask.core.data.repository

import com.blankon.sociotask.core.common.flow.safeApiFlow
import com.blankon.sociotask.core.data.source.remote.ApiService
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AppRepository {
    override fun getListData() =
        safeApiFlow(
            fetchApi = { apiService.getListData() },
            transformData = { it.map { response -> response.mapToEntity() } }
        )
}
