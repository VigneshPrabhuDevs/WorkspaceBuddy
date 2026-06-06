package com.vpdevs.workspacebuddy.domain.usecase

import com.vpdevs.workspacebuddy.domain.model.Task
import com.vpdevs.workspacebuddy.domain.repository.TaskRepository

class DeleteTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) {
        repository.deleteTask(task)
    }
}
