package com.vpdevs.workspacebuddy.domain.usecase

import com.vpdevs.workspacebuddy.domain.model.Priority
import com.vpdevs.workspacebuddy.domain.model.Task
import com.vpdevs.workspacebuddy.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasksUseCase(private val repository: TaskRepository) {
    operator fun invoke(priority: Priority? = null): Flow<List<Task>> {
        return if (priority == null) {
            repository.getTasks()
        } else {
            repository.getTasksByPriority(priority)
        }
    }
}
