package com.vpdevs.workspacebuddy.presentation.task

import com.vpdevs.workspacebuddy.domain.model.Priority
import com.vpdevs.workspacebuddy.domain.model.Task

data class TaskViewState(
    val tasks: List<Task> = emptyList(),
    val filter: Priority? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface TaskUiIntent {
    data object LoadTasks : TaskUiIntent
    data class AddTask(
        val title: String,
        val description: String,
        val priority: Priority,
        val dueDateIso: String? = null
    ) : TaskUiIntent
    data class DeleteTask(val task: Task) : TaskUiIntent
    data class ToggleTask(val task: Task) : TaskUiIntent
    data class ChangeFilter(val priority: Priority?) : TaskUiIntent
}

sealed interface TaskSideEffect {
    data class ShowError(val message: String) : TaskSideEffect
}
