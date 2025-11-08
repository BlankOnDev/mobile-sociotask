package com.blankon.sociotask.feature.home.usecase

import com.blankon.sociotask.core.domain.model.Balance
import com.blankon.sociotask.core.domain.model.Task
import com.blankon.sociotask.core.domain.repository.TaskRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetDashboardUseCase @Inject constructor(
    private val repo: TaskRepository
) {
    data class Dashboard(val balance: Balance, val tasks: List<Task>)

    operator fun invoke(): Flow<Dashboard> = combine(
        repo.getAllBalance(),
        repo.getAllTasks()
    ) { bal, tasks -> Dashboard(bal, tasks) }
}