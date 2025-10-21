package com.blankon.sociotask.core.domain.dashboard.repository

import com.blankon.sociotask.core.domain.DomainError
import com.blankon.sociotask.core.domain.Result
import com.blankon.sociotask.core.domain.dashboard.model.Balance
import com.blankon.sociotask.core.domain.dashboard.model.Task
import com.blankon.sociotask.core.domain.dashboard.model.TaskDraft
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun observeBalance(): Flow<Balance>
    fun observeTasks(): Flow<List<Task>>
    suspend fun createTask(draft: TaskDraft): Result<Task, DomainError>
}
