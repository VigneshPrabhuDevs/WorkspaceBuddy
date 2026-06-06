package com.vpdevs.workspacebuddy.domain.repository

import com.vpdevs.workspacebuddy.domain.model.Priority
import com.vpdevs.workspacebuddy.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<Task>>
    fun getTasksByPriority(priority: Priority): Flow<List<Task>>
    suspend fun getTaskById(id: Long): Task?
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(task: Task)
}
