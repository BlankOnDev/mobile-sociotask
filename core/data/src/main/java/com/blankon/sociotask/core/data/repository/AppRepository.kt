package com.blankon.sociotask.core.data.repository

import com.blankon.sociotask.core.common.utils.state.ApiState
import com.blankon.sociotask.core.data.model.local.SampleModelEntity
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getListData(): Flow<ApiState<List<SampleModelEntity>>>
}