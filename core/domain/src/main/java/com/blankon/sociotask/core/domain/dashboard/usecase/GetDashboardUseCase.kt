package com.blankon.sociotask.core.domain.dashboard.usecase

import com.blankon.sociotask.core.domain.dashboard.model.Balance
import com.blankon.sociotask.core.domain.dashboard.model.Task
import com.blankon.sociotask.core.domain.dashboard.repository.TaskRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetDashboardUseCase @Inject constructor(
    private val repo: TaskRepository
) {
    data class Dashboard(val balance: Balance, val tasks: List<Task>)

    operator fun invoke(): Flow<Dashboard> = combine(
        repo.observeBalance(),
        repo.observeTasks()
    ) { bal, tasks -> Dashboard(bal, tasks) }
}