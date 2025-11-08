package com.blankon.sociotask.core.domain.repository

import com.blankon.sociotask.core.domain.utils.DomainError
import com.blankon.sociotask.core.domain.utils.Result
import com.blankon.sociotask.core.domain.model.Balance
import com.blankon.sociotask.core.domain.model.Task
import com.blankon.sociotask.core.domain.model.TaskDraft
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllBalance(): Flow<Balance>
    fun getAllTasks(page: Int = 1): Flow<List<Task>>
    suspend fun createTask(draft: TaskDraft): Result<Task, DomainError>
}