package com.vpdevs.workspacebuddy.domain.usecase

import com.vpdevs.workspacebuddy.domain.model.Task
import com.vpdevs.workspacebuddy.domain.repository.TaskRepository

class ToggleTaskCompletionUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) {
        repository.updateTask(task.copy(isCompleted = !task.isCompleted))
    }
}
