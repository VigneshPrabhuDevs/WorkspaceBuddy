package com.vpdevs.workspacebuddy.data.repository

import com.vpdevs.workspacebuddy.data.local.TaskDao
import com.vpdevs.workspacebuddy.data.mapper.toDomain
import com.vpdevs.workspacebuddy.data.mapper.toEntity
import com.vpdevs.workspacebuddy.domain.model.Priority
import com.vpdevs.workspacebuddy.domain.model.Task
import com.vpdevs.workspacebuddy.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val dao: TaskDao
) : TaskRepository {

    override fun getTasks(): Flow<List<Task>> {
        return dao.getTasks().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTasksByPriority(priority: Priority): Flow<List<Task>> {
        return dao.getTasksByPriority(priority).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTaskById(id: Long): Task? {
        return dao.getTaskById(id)?.toDomain()
    }

    override suspend fun insertTask(task: Task) {
        dao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        dao.updateTask(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task.toEntity())
    }
}
